package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetIndicatorStateReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";

  private byte deviceID;

  private short sequenceNumber;

  private int state;

  public static GetIndicatorStateReply readGetIndicatorStateReply(byte deviceID,
      short sequenceNumber, X11Input in) throws IOException {
    GetIndicatorStateReply.GetIndicatorStateReplyBuilder javaBuilder = GetIndicatorStateReply.builder();
    int length = in.readCard32();
    int state = in.readCard32();
    byte[] pad5 = in.readPad(20);
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.state(state);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(deviceID);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(state);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetIndicatorStateReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
