package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AllowDeviceEvents implements OneWayRequest, XinputObject {
  public static final byte OPCODE = 19;

  private int time;

  private byte mode;

  private byte deviceId;

  public byte getOpCode() {
    return OPCODE;
  }

  public static AllowDeviceEvents readAllowDeviceEvents(X11Input in) throws IOException {
    AllowDeviceEvents.AllowDeviceEventsBuilder javaBuilder = AllowDeviceEvents.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int time = in.readCard32();
    byte mode = in.readCard8();
    byte deviceId = in.readCard8();
    byte[] pad6 = in.readPad(2);
    javaBuilder.time(time);
    javaBuilder.mode(mode);
    javaBuilder.deviceId(deviceId);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(time);
    out.writeCard8(mode);
    out.writeCard8(deviceId);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class AllowDeviceEventsBuilder {
    public AllowDeviceEvents.AllowDeviceEventsBuilder mode(DeviceInputMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public AllowDeviceEvents.AllowDeviceEventsBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
