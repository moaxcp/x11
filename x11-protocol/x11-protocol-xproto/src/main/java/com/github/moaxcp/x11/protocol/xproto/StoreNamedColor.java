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
public class StoreNamedColor implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 90;

  private byte flags;

  private int cmap;

  private int pixel;

  @NonNull
  private List<Byte> name;

  public byte getOpCode() {
    return OPCODE;
  }

  public static StoreNamedColor readStoreNamedColor(X11Input in) throws IOException {
    StoreNamedColor.StoreNamedColorBuilder javaBuilder = StoreNamedColor.builder();
    byte flags = in.readCard8();
    short length = in.readCard16();
    int cmap = in.readCard32();
    int pixel = in.readCard32();
    short nameLen = in.readCard16();
    byte[] pad6 = in.readPad(2);
    List<Byte> name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.flags(flags);
    javaBuilder.cmap(cmap);
    javaBuilder.pixel(pixel);
    javaBuilder.name(name);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(flags);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
    out.writeCard32(pixel);
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
    out.writePad(2);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  public boolean isFlagsEnabled(@NonNull ColorFlag... maskEnums) {
    for(ColorFlag m : maskEnums) {
      if(!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 16 + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class StoreNamedColorBuilder {
    public boolean isFlagsEnabled(@NonNull ColorFlag... maskEnums) {
      for(ColorFlag m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public StoreNamedColor.StoreNamedColorBuilder flagsEnable(ColorFlag... maskEnums) {
      for(ColorFlag m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public StoreNamedColor.StoreNamedColorBuilder flagsDisable(ColorFlag... maskEnums) {
      for(ColorFlag m : maskEnums) {
        flags((byte) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 16 + 1 * name.size();
    }
  }
}
