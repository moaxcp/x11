package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetInputFocusReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private byte revertTo;

  private short sequenceNumber;

  private int focus;

  public static GetInputFocusReply readGetInputFocusReply(byte revertTo, short sequenceNumber,
      X11Input in) throws IOException {
    GetInputFocusReply.GetInputFocusReplyBuilder javaBuilder = GetInputFocusReply.builder();
    int length = in.readCard32();
    int focus = in.readCard32();
    in.readPad(20);
    javaBuilder.revertTo(revertTo);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.focus(focus);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(revertTo);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(focus);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetInputFocusReplyBuilder {
    public GetInputFocusReply.GetInputFocusReplyBuilder revertTo(InputFocus revertTo) {
      this.revertTo = (byte) revertTo.getValue();
      return this;
    }

    public GetInputFocusReply.GetInputFocusReplyBuilder revertTo(byte revertTo) {
      this.revertTo = revertTo;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
