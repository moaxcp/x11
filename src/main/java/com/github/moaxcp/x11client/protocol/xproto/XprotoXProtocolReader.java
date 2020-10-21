package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.*;
import lombok.Getter;

import java.io.IOException;

public class XprotoXProtocolReader implements XProtocolReader {
  @Getter
  private final String name = "xproto";



  @Override
  public boolean supportedEvent(byte number) {
    if(number < 0) {
      throw new IllegalArgumentException("number must be positive");
    }
    if(Byte.toUnsignedInt(number) < 64) {
      return true;
    }
    return false;
  }

  @Override
  public boolean supportedError(byte code) {
    if(Byte.toUnsignedInt(code) < 128) {
      return true;
    }
    return false;
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    switch(number) {
      case 26:
        return CirculateNotifyEvent.readCirculateNotifyEvent(sentEvent, in);
      default:
        throw new IllegalArgumentException("number " + number + " is not supported");
    }
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    switch(code) {
      case 5:
        return AtomError.readAtomEvent(in);
      default:
        throw new IllegalArgumentException("code not supported by " + name);
    }
  }
}
