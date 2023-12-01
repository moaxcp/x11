package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XIAllowEvents implements OneWayRequest, XinputObject {
  public static final byte OPCODE = 53;

  private int time;

  private short deviceid;

  private byte eventMode;

  private int touchid;

  private int grabWindow;

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIAllowEvents readXIAllowEvents(X11Input in) throws IOException {
    XIAllowEvents.XIAllowEventsBuilder javaBuilder = XIAllowEvents.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int time = in.readCard32();
    short deviceid = in.readCard16();
    byte eventMode = in.readCard8();
    byte[] pad6 = in.readPad(1);
    int touchid = in.readCard32();
    int grabWindow = in.readCard32();
    javaBuilder.time(time);
    javaBuilder.deviceid(deviceid);
    javaBuilder.eventMode(eventMode);
    javaBuilder.touchid(touchid);
    javaBuilder.grabWindow(grabWindow);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(time);
    out.writeCard16(deviceid);
    out.writeCard8(eventMode);
    out.writePad(1);
    out.writeCard32(touchid);
    out.writeCard32(grabWindow);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public static class XIAllowEventsBuilder {
    public XIAllowEvents.XIAllowEventsBuilder eventMode(EventMode eventMode) {
      this.eventMode = (byte) eventMode.getValue();
      return this;
    }

    public XIAllowEvents.XIAllowEventsBuilder eventMode(byte eventMode) {
      this.eventMode = eventMode;
      return this;
    }

    public int getSize() {
      return 20;
    }
  }
}
