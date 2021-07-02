package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.XStruct;
import lombok.Builder;
import lombok.Value;

import java.io.IOException;

@Value
@Builder
public class EventForSendEventStruct implements XStruct {

  XEvent event;

  public EventForSendEventStruct(XEvent event) {
    if(!event.getPluginName().equals("Input") && !(event.getNumber() >= 0 && event.getNumber() <= 16)) {
      throw new IllegalArgumentException("Event does not match allowed. Plugin: Input, NUMBER >= 0 and <= 16.");
    }
    this.event = event;
  }

  public static EventForSendEventStruct readEventForSendEventStruct(X11Input in) {
    //todo find type for extension and read it. This is hard because it requires the offset for the extension
    throw new UnsupportedOperationException("read not supported");
  }

  public void write(X11Output out) throws IOException {
    event.write(out);
  }

  @Override
  public int getSize() {
    return 0;
  }

  @Override
  public String getPluginName() {
    return XinputPlugin.NAME;
  }
}
