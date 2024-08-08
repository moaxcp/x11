package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.xproto.PropMode;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class ChangeOutputProperty implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 13;

  private int output;

  private int property;

  private int type;

  private byte format;

  private byte mode;

  private int numUnits;

  @NonNull
  private ByteList data;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeOutputProperty readChangeOutputProperty(X11Input in) throws IOException {
    ChangeOutputProperty.ChangeOutputPropertyBuilder javaBuilder = ChangeOutputProperty.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int output = in.readCard32();
    int property = in.readCard32();
    int type = in.readCard32();
    byte format = in.readCard8();
    byte mode = in.readCard8();
    byte[] pad8 = in.readPad(2);
    int numUnits = in.readCard32();
    ByteList data = in.readVoid((int) ((Integer.toUnsignedLong(numUnits) * Byte.toUnsignedInt(format)) / 8));
    javaBuilder.output(output);
    javaBuilder.property(property);
    javaBuilder.type(type);
    javaBuilder.format(format);
    javaBuilder.mode(mode);
    javaBuilder.numUnits(numUnits);
    javaBuilder.data(data.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
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
