package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.XError;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class X11ErrorException extends RuntimeException {
  private XError error;
  public X11ErrorException(@NonNull XError error) {
    super("Error \"" + error.getCode() + "\" for request \"" + error.getSequenceNumber() + "\"");
    this.error = error;
  }
}
