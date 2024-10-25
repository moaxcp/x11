package com.github.moaxcp.x11.x11client;

import com.github.moaxcp.x11.protocol.*;
import com.github.moaxcp.x11.protocol.xproto.Setup;
import com.github.moaxcp.x11.protocol.xproto.SetupAuthenticate;
import com.github.moaxcp.x11.protocol.xproto.SetupFailed;
import com.github.moaxcp.x11.protocol.xproto.SetupRequest;
import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class X11Connection implements AutoCloseable {
  private final DisplayName displayName;
  private final XAuthority xAuthority;
  private final Setup setup;
  private final boolean bigEndian;
  private final Socket socket;
  private final X11InputStream in;
  private final X11OutputStream out;

  /**
   * Creates an X11Connection with displayName and connects to x11 using the provided socket. The socket must match
   * information in the displayName.
   *
   * @param displayName of x11 server
   * @param socket      for x11 server represented by displayName
   * @throws ConnectionFailureException    if server returns failure
   * @throws NullPointerException          if any parameter is null
   * @throws UnsupportedOperationException if the connection code is authenticate or any other result
   */
  X11Connection(boolean bigEndian, DisplayName displayName, XAuthority xAuthority, Socket socket) throws IOException {
    this.displayName = requireNonNull(displayName, "displayName");
    this.xAuthority = requireNonNull(xAuthority, "xAuthority");
    this.socket = requireNonNull(socket, "socket");
    this.bigEndian = bigEndian;
    //using BufferedInputStream to support mark/reset
    if (bigEndian) {
      in = new X11BigEndianInputStream(new BufferedInputStream(socket.getInputStream(), 128));
      out = new X11BigEndianOutputStream(socket.getOutputStream());
    } else {
      in = new X11LittleEndianInputStream(new BufferedInputStream(socket.getInputStream(), 128));
      out = new X11LittleEndianOutputStream(socket.getOutputStream());
    }
    sendConnectionSetup(bigEndian);
    in.mark(1);
    byte result = in.readInt8();
    in.reset();
    switch (result) {
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

  public DisplayName getDisplayName() {
    return this.displayName;
  }

  public XAuthority getXAuthority() {
    return this.xAuthority;
  }

  public Setup getSetup() {
    return this.setup;
  }

  public boolean getBigEndian() {
    return bigEndian;
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

  private void sendConnectionSetup(boolean bigEndian) throws IOException {
    SetupRequest setup = SetupRequest.builder()
            .byteOrder(bigEndian ? (byte) 'B' : (byte) 'l')
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
   *
   * @param displayName
   * @return
   * @throws NullPointerException if displayName is null.
   */
  public static X11Connection connect(boolean bigEndian, DisplayName displayName, XAuthority xAuthority) throws IOException {
    requireNonNull(xAuthority, "xAuthority");
    Socket socket;

    if (displayName.isForUnixSocket()) {
      AFUNIXSocketAddress address = AFUNIXSocketAddress.of(new File(displayName.getSocketFileName()));
      socket = AFUNIXSocket.connectTo(address);
    } else {
      InetAddress address = InetAddress.getByName(displayName.getHostName());
      socket = new Socket(address, displayName.getPort());
    }

    return new X11Connection(bigEndian, displayName, xAuthority, socket);
  }

  public static X11Connection connect(boolean bigEndian) throws IOException {
    DisplayName name = DisplayName.standard();
    List<XAuthority> authorities = XAuthority.getAuthorities(XAuthority.getXAuthorityFile());
    Optional<XAuthority> authority = XAuthority.getAuthority(authorities, name);
    if (!authority.isPresent()) {
      throw new IllegalStateException("could not find authority for environment");
    }
    return connect(bigEndian, name, authority.get());
  }

  public static X11Connection connect(boolean bigEndian, DisplayName name) throws IOException {
    List<XAuthority> authorities = XAuthority.getAuthorities(XAuthority.getXAuthorityFile());
    Optional<XAuthority> authority = XAuthority.getAuthority(authorities, name);
    if (!authority.isPresent()) {
      throw new IllegalStateException("could not find authority for environment");
    }
    return connect(bigEndian, name, authority.get());
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
