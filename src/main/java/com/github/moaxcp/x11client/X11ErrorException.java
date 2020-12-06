package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.XError;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class X11ErrorException extends RuntimeException {
  private XError error;
  public X11ErrorException(@NonNull XError error) {
    super("Error {\"name\":\"" + error.getClass().getSimpleName() + "\", "
      + "\"code\":\"" + error.getCode() + "\", "
      + "\"sequenceNumber\":\"" + error.getSequenceNumber() + "\", "
      + "\"majorOpcode\":\"" + error.getMajorOpcode() + "\", "
      + "\"minorOpcode\":\"" + error.getMinorOpcode() + "\", ");
    this.error = error;
  }
}
