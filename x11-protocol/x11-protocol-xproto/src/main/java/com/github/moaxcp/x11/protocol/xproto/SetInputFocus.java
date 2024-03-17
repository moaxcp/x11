package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetInputFocus implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 42;

  private byte revertTo;

  private int focus;

  private int time;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetInputFocus readSetInputFocus(X11Input in) throws IOException {
    SetInputFocus.SetInputFocusBuilder javaBuilder = SetInputFocus.builder();
    byte revertTo = in.readCard8();
    short length = in.readCard16();
    int focus = in.readCard32();
    int time = in.readCard32();
    javaBuilder.revertTo(revertTo);
    javaBuilder.focus(focus);
    javaBuilder.time(time);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(revertTo);
    out.writeCard16((short) getLength());
    out.writeCard32(focus);
    out.writeCard32(time);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetInputFocusBuilder {
    public SetInputFocus.SetInputFocusBuilder revertTo(InputFocus revertTo) {
      this.revertTo = (byte) revertTo.getValue();
      return this;
    }

    public SetInputFocus.SetInputFocusBuilder revertTo(byte revertTo) {
      this.revertTo = revertTo;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
