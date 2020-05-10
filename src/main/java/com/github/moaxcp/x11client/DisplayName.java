package com.github.moaxcp.x11client;

import lombok.NonNull;
import lombok.Value;

import static com.github.moaxcp.x11client.Strings.requireNonBlank;

@Value
public class DisplayName {
  String hostName;
  int displayNumber;
  int screenNumber;

  /**
   * Creates a new display name.
   * @param displayName
   * @throws NullPointerException
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
}
