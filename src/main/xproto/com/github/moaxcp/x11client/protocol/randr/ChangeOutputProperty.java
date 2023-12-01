package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.xproto.PropMode;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ChangeOutputProperty implements OneWayRequest, RandrObject {
  public static final byte OPCODE = 13;

  private int output;

  private int property;

  private int type;

  private byte format;

  private byte mode;

  private int numUnits;

  @NonNull
  private List<Byte> data;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeOutputProperty readChangeOutputProperty(X11Input in) throws IOException {
    ChangeOutputProperty.ChangeOutputPropertyBuilder javaBuilder = ChangeOutputProperty.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int output = in.readCard32();
    int property = in.readCard32();
    int type = in.readCard32();
    byte format = in.readCard8();
    byte mode = in.readCard8();
    byte[] pad8 = in.readPad(2);
    int numUnits = in.readCard32();
    List<Byte> data = in.readVoid((int) ((Integer.toUnsignedLong(numUnits) * Byte.toUnsignedInt(format)) / 8));
    javaBuilder.output(output);
    javaBuilder.property(property);
    javaBuilder.type(type);
    javaBuilder.format(format);
    javaBuilder.mode(mode);
    javaBuilder.numUnits(numUnits);
    javaBuilder.data(data);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(output);
    out.writeCard32(property);
    out.writeCard32(type);
    out.writeCard8(format);
    out.writeCard8(mode);
    out.writePad(2);
    out.writeCard32(numUnits);
    out.writeVoid(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 24 + 1 * data.size();
  }

  public static class ChangeOutputPropertyBuilder {
    public ChangeOutputProperty.ChangeOutputPropertyBuilder mode(PropMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public ChangeOutputProperty.ChangeOutputPropertyBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 24 + 1 * data.size();
    }
  }
}
