package com.github.moaxcp.x11client.protocol;

import java.util.Objects;
import lombok.NonNull;
import lombok.Value;

import static com.github.moaxcp.x11client.protocol.ParametersCheck.requireNonBlank;

/**
 * Class representing an X11 display name. A DisplayName may represent a network socket or a unix socket connection. The
 * convention is to use a unix socket if the hostName is null. If the connection is a unix socket the socket file is
 * "/tmp/.X11-unix/X" + displayNumber. If the connection is tcp the port is 6000 + displayNumber.
 */
@Value
public class DisplayName {
  String hostName;
  int displayNumber;
  int screenNumber;

  public static DisplayName standard() {
    return new DisplayName(System.getenv("DISPLAY"));
  }
  public static DisplayName displayName(String displayName) {
    return new DisplayName(displayName);
  }

  /**
   * Creates a new display name. A network display name includes a hostName ("host:0.0"). A unix display name does not
   * have a hostName (":0"). The convention is "${hostName}:${displayNumber}.${screenNumber}".
   * @param displayName conventional connection string for x11
   * @throws NullPointerException if displayName is null
   * @throws NumberFormatException if displayNumber or screenNumber is not a number
   */
  public DisplayName(@NonNull String displayName) {
    requireNonBlank("displayName", displayName);
    int colonIndex = displayName.indexOf(':');
    if(colonIndex == -1) {
      hostName = displayName;
      displayNumber = 0;
      screenNumber = 0;
      return;
    }
    hostName = colonIndex == 0 ? null : displayName.substring(0, colonIndex);
    int dotIndex = displayName.indexOf('.');
    if(dotIndex == -1) {
      displayNumber = Integer.parseInt(displayName.substring(colonIndex + 1));
      screenNumber = 0;
    } else {
      displayNumber = Integer.parseInt(displayName.substring(colonIndex + 1, dotIndex));
      screenNumber = Integer.parseInt(displayName.substring(dotIndex + 1));
    }
  }

  /**
   * Returns true if this is a conventional tcp network display name.
   * @return
   */
  public boolean isForNetworkSocket() {
    return hostName != null;
  }

  /**
   * Returns conventional port used for network connections.
   * @return port number
   */
  public int getPort() {
    return 6000 + displayNumber;
  }

  /**
   * Returns true if this is a conventional unix socket display name.
   * @return
   */
  public boolean isForUnixSocket() {
    return !isForNetworkSocket();
  }

  /**
   * Returns the conventional socket file name used in unix socket connections. ("/tmp/.X11-unix/X${displayNumber}").
   * @return
   */
  public String getSocketFileName() {
    return "/tmp/.X11-unix/X" + displayNumber;
  }

  /**
   * Returns string representation of this DisplayName in the conventional format:
   * "${hostName}:${displayNumber}.${screenNumber}".
   * @return
   */
  public String toString() {
    return String.format("%s:%d.%d", Objects.toString(hostName, ""), displayNumber, screenNumber);
  }
}
