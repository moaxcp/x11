package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XIDeleteProperty implements OneWayRequest {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 58;

  private short deviceid;

  private int property;

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIDeleteProperty readXIDeleteProperty(X11Input in) throws IOException {
    XIDeleteProperty.XIDeletePropertyBuilder javaBuilder = XIDeleteProperty.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceid = in.readCard16();
    byte[] pad4 = in.readPad(2);
    int property = in.readCard32();
    javaBuilder.deviceid(deviceid);
    javaBuilder.property(property);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceid);
    out.writePad(2);
    out.writeCard32(property);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XIDeletePropertyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
