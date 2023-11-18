package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.xproto.PropMode;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ChangeDevicePropertyEightBits implements ChangeDeviceProperty, XinputObject {
  public static final byte OPCODE = 37;

  private int property;

  private int type;

  private byte deviceId;

  private byte format;

  private byte mode;

  private int numItems;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeDevicePropertyEightBits readChangeDevicePropertyEightBits(byte OPCODE,
      byte[] pad1, short length, int property, int type, byte deviceId, byte format, byte mode,
      byte[] pad8, int numItems, X11Input in) throws IOException {
    ChangeDevicePropertyEightBits.ChangeDevicePropertyEightBitsBuilder javaBuilder = ChangeDevicePropertyEightBits.builder();
    javaBuilder.property(property);
    javaBuilder.type(type);
    javaBuilder.deviceId(deviceId);
    javaBuilder.format(format);
    javaBuilder.mode(mode);
    javaBuilder.numItems(numItems);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(property);
    out.writeCard32(type);
    out.writeCard8(deviceId);
    out.writeCard8(format);
    out.writeCard8(mode);
    out.writePad(1);
    out.writeCard32(numItems);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public static class ChangeDevicePropertyEightBitsBuilder {
    public ChangeDevicePropertyEightBits.ChangeDevicePropertyEightBitsBuilder format(
        PropertyFormat format) {
      this.format = (byte) format.getValue();
      return this;
    }

    public ChangeDevicePropertyEightBits.ChangeDevicePropertyEightBitsBuilder format(byte format) {
      this.format = format;
      return this;
    }

    public ChangeDevicePropertyEightBits.ChangeDevicePropertyEightBitsBuilder mode(PropMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public ChangeDevicePropertyEightBits.ChangeDevicePropertyEightBitsBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 20;
    }
  }
}
