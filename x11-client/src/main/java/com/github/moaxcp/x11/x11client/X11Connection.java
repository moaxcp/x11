package com.github.moaxcp.x11.x11client;

import com.github.moaxcp.x11.protocol.*;
import com.github.moaxcp.x11.protocol.xproto.Setup;
import com.github.moaxcp.x11.protocol.xproto.SetupAuthenticate;
import com.github.moaxcp.x11.protocol.xproto.SetupFailed;
import com.github.moaxcp.x11.protocol.xproto.SetupRequest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.NonNull;
import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

public class X11Connection implements AutoCloseable {
  @Getter
  private final DisplayName displayName;
  @Getter
  private final XAuthority xAuthority;
  @Getter
  private final Setup setup;

  private final Socket socket;
  private final X11InputStream in;
  private final X11OutputStream out;

  /**
   * Creates an X11Connection with displayName and connects to x11 using the provided socket. The socket must match
   * information in the displayName.
   * @param displayName of x11 server
   * @param socket for x11 server represented by displayName
   * @throws ConnectionFailureException if server returns failure
   * @throws NullPointerException if any parameter is null
   * @throws UnsupportedOperationException if the connection code is authenticate or any other result
   */
  X11Connection(@NonNull DisplayName displayName, @NonNull XAuthority xAuthority, @NonNull Socket socket) throws IOException {
    this.displayName = displayName;
    this.xAuthority = xAuthority;
    this.socket = socket;
    //using BufferedInputStream to support mark/reset
    in = new X11InputStream(new BufferedInputStream(socket.getInputStream(), 128));
    out = new X11OutputStream(socket.getOutputStream());
    sendConnectionSetup();
    in.mark(1);
    byte result = in.readInt8();
    in.reset();
    switch(result) {
      case 0: //failure
        SetupFailed failure = SetupFailed.readSetupFailed(getX11Input());
        throw new ConnectionFailureException(failure);
      case 1: //success
        setup = Setup.readSetup(getX11Input());
        break;
      case 2: //authenticate
        SetupAuthenticate authenticate = SetupAuthenticate.readSetupAuthenticate(getX11Input());
        throw new UnsupportedOperationException("authenticate not supported " + authenticate);
      default:
        throw new UnsupportedOperationException(result + " not supported");
    }
  }

  public X11Input getX11Input() {
    return in;
  }

  public int inputAvailable() {
    try {
      return in.available();
    } catch (IOException e) {
      throw new X11ClientException(e.getMessage(), e);
    }
  }

  public X11Output getX11Output() {
    return out;
  }

  public String getHostName() {
    return socket.getInetAddress().getHostName();
  }

  public int getPort() {
    return socket.getPort();
  }

  private void sendConnectionSetup() throws IOException {
    SetupRequest setup = SetupRequest.builder()
      .byteOrder((byte) 'B')
      .protocolMajorVersion((short) 11)
      .protocolMinorVersion((short) 0)
      .authorizationProtocolName(xAuthority.getProtocolName())
      .authorizationProtocolData(xAuthority.getProtocolData())
      .build();
    setup.write(out);
    out.flush();
  }

  /**
   * Creates an X11Connection with displayName.
   * @param displayName
   * @return
   * @throws NullPointerException if displayName is null.
   */
  public static X11Connection connect(@NonNull DisplayName displayName, @NonNull XAuthority xAuthority) throws IOException {
    Socket socket;

    if (displayName.isForUnixSocket()) {
      AFUNIXSocketAddress address = new AFUNIXSocketAddress(new File(displayName.getSocketFileName()));
      socket = AFUNIXSocket.connectTo(address);
    } else {
      InetAddress address = InetAddress.getByName(displayName.getHostName());
      socket = new Socket(address, displayName.getPort());
    }

    return new X11Connection(displayName, xAuthority, socket);
  }

  public static X11Connection connect() throws IOException {
    DisplayName name = DisplayName.standard();
    List<XAuthority> authorities = XAuthority.getAuthorities(XAuthority.getXAuthorityFile());
    Optional<XAuthority> authority = XAuthority.getAuthority(authorities, name);
    if(!authority.isPresent()) {
      throw new IllegalStateException("could not find authority for environment");
    }
    return connect(name, authority.get());
  }

  public static X11Connection connect(@NonNull DisplayName name) throws IOException {
    List<XAuthority> authorities = XAuthority.getAuthorities(XAuthority.getXAuthorityFile());
    Optional<XAuthority> authority = XAuthority.getAuthority(authorities, name);
    if(!authority.isPresent()) {
      throw new IllegalStateException("could not find authority for environment");
    }
    return connect(name, authority.get());
  }

  @Override
  public void close() throws IOException {
    try {
      out.close();
    } finally {
      try {
        in.close();
      } finally {
        socket.close();
      }
    }
  }
}
