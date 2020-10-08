package com.github.moaxcp.x11client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.xproto.SetupAuthenticateStruct;
import com.github.moaxcp.x11client.protocol.xproto.SetupFailedStruct;
import com.github.moaxcp.x11client.protocol.xproto.SetupRequestStruct;
import com.github.moaxcp.x11client.protocol.xproto.SetupStruct;
import lombok.Getter;
import lombok.NonNull;
import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import static com.github.moaxcp.x11client.XAuthority.*;

public class X11Connection implements AutoCloseable {
  @Getter
  private final DisplayName displayName;
  @Getter
  private final XAuthority xAuthority;
  @Getter
  private final SetupStruct setupStruct;

  private final Socket socket;
  private final X11InputStream in;
  private final X11OutputStream out;

  /**
   * Creates an X11Connection with displayName and connects to x11 using the provided socket. The socket must match
   * information in the displayName.
   * @param displayName of x11 server
   * @param socket for x11 server represented by displayName
   * @throws NullPointerException if any parameter is null
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
        SetupFailedStruct failure = SetupFailedStruct.readSetupFailedStruct(getX11Input());
        throw new ConnectionFailureException(failure);
      case 1: //success
        setupStruct = SetupStruct.readSetupStruct(getX11Input());
        break;
      case 2: //authenticate
        SetupAuthenticateStruct authenticate = SetupAuthenticateStruct.readSetupAuthenticateStruct(getX11Input());
        throw new UnsupportedOperationException("authenticate not supported " + authenticate);
      default:
        throw new UnsupportedOperationException(result + " not supported");
    }
  }

  public X11Input getX11Input() {
    return in;
  }

  public X11Output getX11Output() {
    return out;
  }

  private void sendConnectionSetup() throws IOException {
    SetupRequestStruct setup = new SetupRequestStruct();
    setup.setByteOrder((byte) 'B');
    setup.setProtocolMajorVersion((short) 11);
    setup.setProtocolMinorVersion((short) 0);
    setup.setAuthorizationProtocolName(xAuthority.getProtocolName());
    setup.setAuthorizationProtocolData(xAuthority.getProtocolData());
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
    List<XAuthority> authorities = getAuthorities(getXAuthorityFile());
    Optional<XAuthority> authority = getAuthority(authorities, name);
    if(!authority.isPresent()) {
      throw new IllegalStateException("could not find authority for environment");
    }
    return connect(name, authority.get());
  }

  public static X11Connection connect(@NonNull DisplayName name) throws IOException {
    List<XAuthority> authorities = getAuthorities(getXAuthorityFile());
    Optional<XAuthority> authority = getAuthority(authorities, name);
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
