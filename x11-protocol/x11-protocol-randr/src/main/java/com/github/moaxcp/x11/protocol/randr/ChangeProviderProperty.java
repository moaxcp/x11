package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class ChangeProviderProperty implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 39;

  private int provider;

  private int property;

  private int type;

  private byte format;

  private byte mode;

  private int numItems;

  @NonNull
  private ByteList data;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeProviderProperty readChangeProviderProperty(X11Input in) throws IOException {
    ChangeProviderProperty.ChangeProviderPropertyBuilder javaBuilder = ChangeProviderProperty.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int provider = in.readCard32();
    int property = in.readCard32();
    int type = in.readCard32();
    byte format = in.readCard8();
    byte mode = in.readCard8();
    byte[] pad8 = in.readPad(2);
    int numItems = in.readCard32();
    ByteList data = in.readVoid((int) (Integer.toUnsignedLong(numItems) * (Byte.toUnsignedInt(format) / 8)));
    javaBuilder.provider(provider);
    javaBuilder.property(property);
    javaBuilder.type(type);
    javaBuilder.format(format);
    javaBuilder.mode(mode);
    javaBuilder.numItems(numItems);
    javaBuilder.data(data.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ChangeProviderPropertyBuilder {
    public int getSize() {
      return 24 + 1 * data.size();
    }
  }
}
