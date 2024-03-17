package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XIUngrabDevice implements OneWayRequest {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 52;

  private int time;

  private short deviceid;

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIUngrabDevice readXIUngrabDevice(X11Input in) throws IOException {
    XIUngrabDevice.XIUngrabDeviceBuilder javaBuilder = XIUngrabDevice.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int time = in.readCard32();
    short deviceid = in.readCard16();
    byte[] pad5 = in.readPad(2);
    javaBuilder.time(time);
    javaBuilder.deviceid(deviceid);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(time);
    out.writeCard16(deviceid);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XIUngrabDeviceBuilder {
    public int getSize() {
      return 12;
    }
  }
}
