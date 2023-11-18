package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XIDeleteProperty implements OneWayRequest, XinputObject {
  public static final byte OPCODE = 58;

  private short deviceid;

  private int property;

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIDeleteProperty readXIDeleteProperty(X11Input in) throws IOException {
    XIDeleteProperty.XIDeletePropertyBuilder javaBuilder = XIDeleteProperty.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short deviceid = in.readCard16();
    byte[] pad4 = in.readPad(2);
    int property = in.readCard32();
    javaBuilder.deviceid(deviceid);
    javaBuilder.property(property);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(deviceid);
    out.writePad(2);
    out.writeCard32(property);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class XIDeletePropertyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
