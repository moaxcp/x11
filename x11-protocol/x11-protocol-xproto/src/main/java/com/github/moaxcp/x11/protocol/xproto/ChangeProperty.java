package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ChangeProperty implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 18;

  private byte mode;

  private int window;

  private int property;

  private int type;

  private byte format;

  private int dataLen;

  @NonNull
  private List<Byte> data;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeProperty readChangeProperty(X11Input in) throws IOException {
    ChangeProperty.ChangePropertyBuilder javaBuilder = ChangeProperty.builder();
    byte mode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int property = in.readCard32();
    int type = in.readCard32();
    byte format = in.readCard8();
    byte[] pad7 = in.readPad(3);
    int dataLen = in.readCard32();
    List<Byte> data = in.readVoid((int) ((Integer.toUnsignedLong(dataLen) * Byte.toUnsignedInt(format)) / 8));
    javaBuilder.mode(mode);
    javaBuilder.window(window);
    javaBuilder.property(property);
    javaBuilder.type(type);
    javaBuilder.format(format);
    javaBuilder.dataLen(dataLen);
    javaBuilder.data(data);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(mode);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(property);
    out.writeCard32(type);
    out.writeCard8(format);
    out.writePad(3);
    out.writeCard32(dataLen);
    out.writeVoid(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 24 + 1 * data.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ChangePropertyBuilder {
    public ChangeProperty.ChangePropertyBuilder mode(PropMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public ChangeProperty.ChangePropertyBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 24 + 1 * data.size();
    }
  }
}
