package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class ConfigureProviderProperty implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 38;

  private int provider;

  private int property;

  private boolean pending;

  private boolean range;

  @NonNull
  private IntList values;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ConfigureProviderProperty readConfigureProviderProperty(X11Input in) throws
      IOException {
    ConfigureProviderProperty.ConfigureProviderPropertyBuilder javaBuilder = ConfigureProviderProperty.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int provider = in.readCard32();
    javaStart += 4;
    int property = in.readCard32();
    javaStart += 4;
    boolean pending = in.readBool();
    javaStart += 1;
    boolean range = in.readBool();
    javaStart += 1;
    byte[] pad7 = in.readPad(2);
    javaStart += 2;
    IntList values = in.readInt32(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.provider(provider);
    javaBuilder.property(property);
    javaBuilder.pending(pending);
    javaBuilder.range(range);
    javaBuilder.values(values.toImmutable());
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ConfigureProviderPropertyBuilder {
    public int getSize() {
      return 16 + 4 * values.size();
    }
  }
}
