package com.github.moaxcp.x11.protocol;

import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.moaxcp.x11.protocol.ParametersCheck.requireNonEmpty;
import static com.github.moaxcp.x11.protocol.Utilities.toList;

/**
 * An X11 Authority defines a secret key used when authenticating with the x11 server. family, address and displayNumber
 * are used to find the correct authority. protocolName and protocolDate is used to authenticate with x11.
 */
@Value
public class XAuthority {
  @NonNull Family family;
  @NonNull List<Byte> address;
  @NonNull String displayNumber;
  @NonNull List<Byte> protocolName;
  @NonNull List<Byte> protocolData;

  /**
   * Represents a Family or connection type used in authentication.
   */
  @ToString
  public enum Family {
    INTERNET(0),
    DEC_NET(1),
    CHAOS(2),
    SERVER_INTERPRETED(5),
    INTERNET6(6),
    LOCAL(256),
    WILD(65535),
    NETNAME(254),
    KRB5PRINCIPAL(253),
    LOCALHOST(252);

    private final int code;

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
   * @throws IllegalArgumentException if displayNumber is less than 0 or protocolName is empty.
   */
  public XAuthority(@NonNull Family family, @NonNull List<Byte> address, @NonNull String displayNumber, @NonNull List<Byte> protocolName, @NonNull List<Byte> protocolData) {
    this.family = family;
    this.address = address;
    this.displayNumber = displayNumber;
    this.protocolName = requireNonEmpty("protocolName", protocolName);
    this.protocolData = requireNonEmpty("protocolData", protocolData);
  }

  public static Optional<XAuthority> read(DataInput in) {
    try {
      Family family = Family.getByCode(in.readUnsignedShort());
      int dataLength = in.readUnsignedShort();
      List<Byte> address = readBytes(in, dataLength);
      dataLength = in.readUnsignedShort();
      List<Byte> numberBytes = readBytes(in, dataLength);
      String number;
      if(dataLength == 0){
        number = "0";
      } else {
        number = Utilities.toString(numberBytes, StandardCharsets.UTF_8);
      }
      dataLength = in.readUnsignedShort();
      List<Byte> name = readBytes(in, dataLength);
      dataLength = in.readUnsignedShort();
      List<Byte> data = readBytes(in, dataLength);
      return Optional.of(new XAuthority(family, address, number, name, data));
    } catch (IOException ex) {
      return Optional.empty();
    }
  }

  private static List<Byte> readBytes(DataInput in, int length) throws IOException {
    byte[] bytes = new byte[length];
    in.readFully(bytes);
    return toList(bytes);
  }

  public static Optional<XAuthority> getAuthority(List<XAuthority> authorities, DisplayName displayName) throws UnknownHostException {
    for (int i = 0; i < authorities.size(); i++) {
      XAuthority auth = authorities.get(i);
      switch(auth.getFamily()) {
        case WILD:
          return Optional.of(auth);
        default:
          InetAddress hostNameAddress;
          if(displayName.getHostName() == null || displayName.getHostName().equals("localhost")) {
            hostNameAddress = InetAddress.getLocalHost();
          } else {
            hostNameAddress = InetAddress.getByName(displayName.getHostName());
          }
          try {
            InetAddress authAddress = InetAddress.getByName(Utilities.toString(auth.getAddress(), StandardCharsets.UTF_8));
            if(authAddress.equals(hostNameAddress)) {
              return Optional.of(auth);
            }
          } catch (UnknownHostException e) {
            //go to next authority
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
