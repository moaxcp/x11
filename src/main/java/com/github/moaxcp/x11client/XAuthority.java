package com.github.moaxcp.x11client;

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
  public XAuthority(@NonNull Family family, @NonNull byte[] address, int displayNumber, @NonNull String protocolName, @NonNull byte[] protocolData) {
    this.family = family;
    this.address = requireNonEmpty("address", address);
    if(displayNumber < 0) {
      throw new IllegalArgumentException("displayNumber was \"" + displayNumber + "\" expected >= 0.");
    }
    this.displayNumber = displayNumber;
    this.protocolName = requireNonBlank("protocolName", protocolName);
    this.protocolData = requireNonEmpty("protocolData", protocolData);
  }
}
