package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.xproto.PropMode;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XIChangePropertySixteenBits implements XIChangeProperty, XinputObject {
  public static final byte OPCODE = 57;

  private short deviceid;

  private byte mode;

  private byte format;

  private int property;

  private int type;

  private int numItems;

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIChangePropertySixteenBits readXIChangePropertySixteenBits(byte OPCODE,
      byte[] pad1, short length, short deviceid, byte mode, byte format, int property, int type,
      int numItems, X11Input in) throws IOException {
    XIChangePropertySixteenBits.XIChangePropertySixteenBitsBuilder javaBuilder = XIChangePropertySixteenBits.builder();
    javaBuilder.deviceid(deviceid);
    javaBuilder.mode(mode);
    javaBuilder.format(format);
    javaBuilder.property(property);
    javaBuilder.type(type);
    javaBuilder.numItems(numItems);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(deviceid);
    out.writeCard8(mode);
    out.writeCard8(format);
    out.writeCard32(property);
    out.writeCard32(type);
    out.writeCard32(numItems);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public static class XIChangePropertySixteenBitsBuilder {
    public XIChangePropertySixteenBits.XIChangePropertySixteenBitsBuilder mode(PropMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public XIChangePropertySixteenBits.XIChangePropertySixteenBitsBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public XIChangePropertySixteenBits.XIChangePropertySixteenBitsBuilder format(
        PropertyFormat format) {
      this.format = (byte) format.getValue();
      return this;
    }

    public XIChangePropertySixteenBits.XIChangePropertySixteenBitsBuilder format(byte format) {
      this.format = format;
      return this;
    }

    public int getSize() {
      return 20;
    }
  }
}
