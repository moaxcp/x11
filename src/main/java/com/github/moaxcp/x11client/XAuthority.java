package com.github.moaxcp.x11client;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import lombok.*;

import static com.github.moaxcp.x11client.ParametersCheck.requireNonBlank;
import static com.github.moaxcp.x11client.ParametersCheck.requireNonEmpty;

/**
 * An X11 Authority defines a secret key used when authenticating with the x11 server. family, address and displayNumber
 * are used to find the correct authority. protocolName and protocolDate is used to authenticate with x11.
 */
@Value
public class XAuthority {
  @NonNull Family family;
  @NonNull byte[] address;
  int displayNumber;
  @NonNull String protocolName;
  @NonNull byte[] protocolData;

  /**
   * Represents a Family or connection type used in authentication.
   */
  @ToString
  public enum Family {
    INTERNET(0),
    LOCAL(256),
    WILD(65535),
    KRB5PRINCIPAL(254),
    LOCALHOST(252);

    private int code;

    Family(int code) {
      this.code = code;
    }

    /**
     * Returns the family code
     * @return
     */
    public int getCode() {
      return this.code;
    }

    /**
     * Returns the family with this code
     * @param code
     * @return
     * @throws IllegalArgumentException if code is unsupported.
     */
    public static Family getByCode(int code) {
      for(Family family : Family.values()) {
        if(family.code == code) {
          return family;
        }
      }
      throw new IllegalArgumentException("Unsupported code \"" + code + "\"");
    }
  }

  /**
   * Creates a new XAuthority.
   * @param family
   * @param address
   * @param displayNumber
   * @param protocolName
   * @param protocolData
   * @throws NullPointerException if any parameter is null.
   * @throws IllegalArgumentException if displayNumber is < 0 or protocolName is empty.
   */
  XAuthority(@NonNull Family family, @NonNull byte[] address, int displayNumber, @NonNull String protocolName, @NonNull byte[] protocolData) {
    this.family = family;
    this.address = requireNonEmpty("address", address);
    if(displayNumber < 0) {
      throw new IllegalArgumentException("displayNumber was \"" + displayNumber + "\" expected >= 0.");
    }
    this.displayNumber = displayNumber;
    this.protocolName = requireNonBlank("protocolName", protocolName);
    this.protocolData = requireNonEmpty("protocolData", protocolData);
  }

  public static Optional<XAuthority> read(DataInput in) {
    try {
      Family family = Family.getByCode(in.readUnsignedShort());
      int dataLength = in.readUnsignedShort();
      byte[] address = readBytes(in, dataLength);
      int number = Integer.parseInt(in.readUTF());
      String name = in.readUTF();
      dataLength = in.readUnsignedShort();
      byte[] data = readBytes(in, dataLength);
      return Optional.of(new XAuthority(family, address, number, name, data));
    } catch (IOException ex) {
      return Optional.empty();
    }
  }

  private static byte[] readBytes(DataInput in, int length) throws IOException {
    byte[] bytes = new byte[length];
    in.readFully(bytes);
    return bytes;
  }

  public static Optional<XAuthority> getAuthority(List<XAuthority> authorities, DisplayName displayName) throws UnknownHostException {
    for (int i = 0; i < authorities.size(); i++) {
      XAuthority auth = authorities.get(i);
      switch(auth.getFamily()) {
        case WILD:
          return Optional.of(auth);
        default:
          InetAddress authAddress = InetAddress.getByName(new String(auth.getAddress(), StandardCharsets.UTF_8));
          InetAddress hostNameAddress;
          if(displayName.getHostName() == null || displayName.getHostName().equals("localhost")) {
            hostNameAddress = InetAddress.getLocalHost();
          } else {
            hostNameAddress = InetAddress.getByName(displayName.getHostName());
          }
          if(authAddress.equals(hostNameAddress)) {
            return Optional.of(auth);
          }
          break;
      }
    }
    return Optional.empty();
  }

  /**
   * Returns the XAuthority file for the current environment. First the XAUTHORITY environment variable is checked. If
   * XAUTHORITY is not set or empty the .XAuthority file in the user's home is returned.
   * @return XAuthority file to use for the current environment
   */
  public static File getXAuthorityFile() {
    String authFilename = System.getenv("XAUTHORITY");
    if (authFilename == null || authFilename.equals("")) {
      authFilename = System.getProperty("user.home") + File.separatorChar + ".Xauthority";
    }
    return new File(authFilename);
  }

  public static List<XAuthority> getAuthorities(File file) {
    List<XAuthority> authorities = new ArrayList<>();
    try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
      Optional<XAuthority> read = read(in);
      while(read.isPresent()) {
        XAuthority current = read.get();
        authorities.add(current);
        read = read(in);
      }
    } catch(IOException ex) {
      throw new UncheckedIOException(ex);
    }
    return authorities;
  }
}
