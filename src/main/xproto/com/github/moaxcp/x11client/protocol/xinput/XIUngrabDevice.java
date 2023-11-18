package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XIUngrabDevice implements OneWayRequest, XinputObject {
  public static final byte OPCODE = 52;

  private int time;

  private short deviceid;

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIUngrabDevice readXIUngrabDevice(X11Input in) throws IOException {
    XIUngrabDevice.XIUngrabDeviceBuilder javaBuilder = XIUngrabDevice.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int time = in.readCard32();
    short deviceid = in.readCard16();
    byte[] pad5 = in.readPad(2);
    javaBuilder.time(time);
    javaBuilder.deviceid(deviceid);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(time);
    out.writeCard16(deviceid);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class XIUngrabDeviceBuilder {
    public int getSize() {
      return 12;
    }
  }
}
