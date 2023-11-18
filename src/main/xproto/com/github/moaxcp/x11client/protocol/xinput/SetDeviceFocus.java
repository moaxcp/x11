package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.xproto.InputFocus;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetDeviceFocus implements OneWayRequest, XinputObject {
  public static final byte OPCODE = 21;

  private int focus;

  private int time;

  private byte revertTo;

  private byte deviceId;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetDeviceFocus readSetDeviceFocus(X11Input in) throws IOException {
    SetDeviceFocus.SetDeviceFocusBuilder javaBuilder = SetDeviceFocus.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int focus = in.readCard32();
    int time = in.readCard32();
    byte revertTo = in.readCard8();
    byte deviceId = in.readCard8();
    byte[] pad7 = in.readPad(2);
    javaBuilder.focus(focus);
    javaBuilder.time(time);
    javaBuilder.revertTo(revertTo);
    javaBuilder.deviceId(deviceId);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(focus);
    out.writeCard32(time);
    out.writeCard8(revertTo);
    out.writeCard8(deviceId);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class SetDeviceFocusBuilder {
    public SetDeviceFocus.SetDeviceFocusBuilder revertTo(InputFocus revertTo) {
      this.revertTo = (byte) revertTo.getValue();
      return this;
    }

    public SetDeviceFocus.SetDeviceFocusBuilder revertTo(byte revertTo) {
      this.revertTo = revertTo;
      return this;
    }

    public int getSize() {
      return 16;
    }
  }
}
