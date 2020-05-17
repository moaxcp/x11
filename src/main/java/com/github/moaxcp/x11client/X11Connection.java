package com.github.moaxcp.x11client;

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

import static com.github.moaxcp.x11client.XAuthority.*;

public class X11Connection implements AutoCloseable {
  @Getter
  private final DisplayName displayName;
  @Getter
  private final XAuthority xAuthority;
  @Getter
  private final ConnectionSuccess connectionSuccess;

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
  public X11Connection(@NonNull DisplayName displayName, @NonNull XAuthority xAuthority, @NonNull Socket socket) throws IOException {
    this.displayName = displayName;
    this.xAuthority = xAuthority;
    this.socket = socket;
    in = new X11InputStream(socket.getInputStream());
    out = new X11OutputStream(socket.getOutputStream());
    sendConnectionSetup();
    int result = in.readInt8();
    switch(result) {
      case 0: //failure
        ConnectionFailure failure = ConnectionFailure.readFailure(in);
        throw new ConnectionFailureException(failure);
      case 1: //success
        connectionSuccess = ConnectionSuccess.readSuccess(in);
        break;
      case 2: //authenticate
      default:
        throw new UnsupportedOperationException("authenticate not supported");
    }
  }

  private void sendConnectionSetup() throws IOException {
    out.writeByte('B');
    out.writeByte(0);
    out.writeCard16(11);
    out.writeCard16(0);
    out.writeCard16(xAuthority.getProtocolName().length());
    out.writeCard16(xAuthority.getProtocolData().length);
    out.writeCard16(0);
    out.writeString8(xAuthority.getProtocolName());
    out.writePad(xAuthority.getProtocolName().length());
    out.writeString8(xAuthority.getProtocolData());
    out.writePad(xAuthority.getProtocolData().length);
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
