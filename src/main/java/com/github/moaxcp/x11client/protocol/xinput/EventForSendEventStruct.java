package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;

public class EventForSendEventStruct implements XObject {
  public static EventForSendEventStruct readEventForSendEventStruct(X11Input in) {
    //todo
    return null;
  }

  public void write(X11Output out) {
  }

  @Override
  public int getSize() {
    return 0;
  }
}
