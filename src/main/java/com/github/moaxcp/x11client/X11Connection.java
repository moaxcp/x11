package com.github.moaxcp.x11client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.NonNull;
import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import static com.github.moaxcp.x11client.XAuthority.*;

@Getter
public class X11Connection implements AutoCloseable {
  private final DisplayName displayName;
  private final XAuthority xAuthority;
  private final Socket socket;
  private final DataInputStream in;
  private final DataOutputStream out;

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
    in = new DataInputStream(socket.getInputStream());
    out = new DataOutputStream(socket.getOutputStream());
    sendByteOrder();
    writeCard16(11);
    writeCard16(0);
    writeString8(xAuthority.getProtocolName());
    writeString8(xAuthority.getProtocolData());
    out.flush();
    int result = readInt8();
  }

  private int readInt8() throws IOException {
    return in.readUnsignedByte();
  }

  private void writeString8(byte[] protocolData) throws IOException {
    out.write(protocolData);
  }

  private void writeString8(String value) throws IOException {
    out.writeUTF(value);
  }

  private void writeCard16(int value) throws IOException {
    out.writeShort(value);
  }

  private int readCard16() throws IOException {
    return in.readUnsignedShort();
  }

  private void sendByteOrder() throws IOException {
    out.write('B');
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
    socket.close();
  }
}
