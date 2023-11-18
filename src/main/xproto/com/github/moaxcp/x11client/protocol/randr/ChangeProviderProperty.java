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
public class ChangeProviderProperty implements OneWayRequest, RandrObject {
  public static final byte OPCODE = 39;

  private int provider;

  private int property;

  private int type;

  private byte format;

  private byte mode;

  private int numItems;

  @NonNull
  private List<Byte> data;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeProviderProperty readChangeProviderProperty(X11Input in) throws IOException {
    ChangeProviderProperty.ChangeProviderPropertyBuilder javaBuilder = ChangeProviderProperty.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int provider = in.readCard32();
    int property = in.readCard32();
    int type = in.readCard32();
    byte format = in.readCard8();
    byte mode = in.readCard8();
    byte[] pad8 = in.readPad(2);
    int numItems = in.readCard32();
    List<Byte> data = in.readVoid((int) (Integer.toUnsignedLong(numItems) * (Byte.toUnsignedInt(format) / 8)));
    javaBuilder.provider(provider);
    javaBuilder.property(property);
    javaBuilder.type(type);
    javaBuilder.format(format);
    javaBuilder.mode(mode);
    javaBuilder.numItems(numItems);
    javaBuilder.data(data);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(provider);
    out.writeCard32(property);
    out.writeCard32(type);
    out.writeCard8(format);
    out.writeCard8(mode);
    out.writePad(2);
    out.writeCard32(numItems);
    out.writeVoid(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 24 + 1 * data.size();
  }

  public static class ChangeProviderPropertyBuilder {
    public int getSize() {
      return 24 + 1 * data.size();
    }
  }
}
