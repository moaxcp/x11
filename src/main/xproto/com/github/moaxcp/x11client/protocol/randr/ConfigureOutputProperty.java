package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ConfigureOutputProperty implements OneWayRequest, RandrObject {
  public static final byte OPCODE = 12;

  private int output;

  private int property;

  private boolean pending;

  private boolean range;

  @NonNull
  private List<Integer> values;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ConfigureOutputProperty readConfigureOutputProperty(X11Input in) throws
      IOException {
    ConfigureOutputProperty.ConfigureOutputPropertyBuilder javaBuilder = ConfigureOutputProperty.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int output = in.readCard32();
    javaStart += 4;
    int property = in.readCard32();
    javaStart += 4;
    boolean pending = in.readBool();
    javaStart += 1;
    boolean range = in.readBool();
    javaStart += 1;
    byte[] pad7 = in.readPad(2);
    javaStart += 2;
    List<Integer> values = in.readInt32(javaStart - length);
    javaBuilder.output(output);
    javaBuilder.property(property);
    javaBuilder.pending(pending);
    javaBuilder.range(range);
    javaBuilder.values(values);
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
    out.writeBool(pending);
    out.writeBool(range);
    out.writePad(2);
    out.writeInt32(values);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + 4 * values.size();
  }

  public static class ConfigureOutputPropertyBuilder {
    public int getSize() {
      return 16 + 4 * values.size();
    }
  }
}
