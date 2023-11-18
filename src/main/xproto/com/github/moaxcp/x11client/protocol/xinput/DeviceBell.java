package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeviceBell implements OneWayRequest, XinputObject {
  public static final byte OPCODE = 32;

  private byte deviceId;

  private byte feedbackId;

  private byte feedbackClass;

  private byte percent;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeviceBell readDeviceBell(X11Input in) throws IOException {
    DeviceBell.DeviceBellBuilder javaBuilder = DeviceBell.builder();
    byte deviceId = in.readCard8();
    short length = in.readCard16();
    byte feedbackId = in.readCard8();
    byte feedbackClass = in.readCard8();
    byte percent = in.readInt8();
    javaBuilder.deviceId(deviceId);
    javaBuilder.feedbackId(feedbackId);
    javaBuilder.feedbackClass(feedbackClass);
    javaBuilder.percent(percent);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(deviceId);
    out.writeCard16((short) getLength());
    out.writeCard8(feedbackId);
    out.writeCard8(feedbackClass);
    out.writeInt8(percent);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 7;
  }

  public static class DeviceBellBuilder {
    public int getSize() {
      return 7;
    }
  }
}
