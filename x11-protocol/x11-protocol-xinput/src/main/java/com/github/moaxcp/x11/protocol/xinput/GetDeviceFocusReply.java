package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.xproto.InputFocus;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetDeviceFocusReply implements XReply {
  public static final String PLUGIN_NAME = "xinput";

  private byte xiReplyType;

  private short sequenceNumber;

  private int focus;

  private int time;

  private byte revertTo;

  public static GetDeviceFocusReply readGetDeviceFocusReply(byte xiReplyType, short sequenceNumber,
      X11Input in) throws IOException {
    GetDeviceFocusReply.GetDeviceFocusReplyBuilder javaBuilder = GetDeviceFocusReply.builder();
    int length = in.readCard32();
    int focus = in.readCard32();
    int time = in.readCard32();
    byte revertTo = in.readCard8();
    byte[] pad7 = in.readPad(15);
    javaBuilder.xiReplyType(xiReplyType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.focus(focus);
    javaBuilder.time(time);
    javaBuilder.revertTo(revertTo);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(xiReplyType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(focus);
    out.writeCard32(time);
    out.writeCard8(revertTo);
    out.writePad(15);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDeviceFocusReplyBuilder {
    public GetDeviceFocusReply.GetDeviceFocusReplyBuilder revertTo(InputFocus revertTo) {
      this.revertTo = (byte) revertTo.getValue();
      return this;
    }

    public GetDeviceFocusReply.GetDeviceFocusReplyBuilder revertTo(byte revertTo) {
      this.revertTo = revertTo;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
