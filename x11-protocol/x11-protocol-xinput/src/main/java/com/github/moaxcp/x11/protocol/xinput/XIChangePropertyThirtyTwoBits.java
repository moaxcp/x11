package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.xproto.PropMode;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XIChangePropertyThirtyTwoBits implements XIChangeProperty {
  public static final String PLUGIN_NAME = "xinput";

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

  public static XIChangePropertyThirtyTwoBits readXIChangePropertyThirtyTwoBits(byte majorOpcode,
      byte OPCODE, short length, short deviceid, byte mode, byte format, int property, int type,
      int numItems, X11Input in) throws IOException {
    XIChangePropertyThirtyTwoBits.XIChangePropertyThirtyTwoBitsBuilder javaBuilder = XIChangePropertyThirtyTwoBits.builder();
    javaBuilder.deviceid(deviceid);
    javaBuilder.mode(mode);
    javaBuilder.format(format);
    javaBuilder.property(property);
    javaBuilder.type(type);
    javaBuilder.numItems(numItems);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XIChangePropertyThirtyTwoBitsBuilder {
    public XIChangePropertyThirtyTwoBits.XIChangePropertyThirtyTwoBitsBuilder mode(PropMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public XIChangePropertyThirtyTwoBits.XIChangePropertyThirtyTwoBitsBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public XIChangePropertyThirtyTwoBits.XIChangePropertyThirtyTwoBitsBuilder format(
        PropertyFormat format) {
      this.format = (byte) format.getValue();
      return this;
    }

    public XIChangePropertyThirtyTwoBits.XIChangePropertyThirtyTwoBitsBuilder format(byte format) {
      this.format = format;
      return this;
    }

    public int getSize() {
      return 20;
    }
  }
}
