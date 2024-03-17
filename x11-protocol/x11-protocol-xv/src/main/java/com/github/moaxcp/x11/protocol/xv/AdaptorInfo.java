package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class AdaptorInfo implements XStruct {
  public static final String PLUGIN_NAME = "xv";

  private int baseId;

  private short numPorts;

  private byte type;

  @NonNull
  private List<Byte> name;

  @NonNull
  private List<Format> formats;

  public static AdaptorInfo readAdaptorInfo(X11Input in) throws IOException {
    AdaptorInfo.AdaptorInfoBuilder javaBuilder = AdaptorInfo.builder();
    int baseId = in.readCard32();
    short nameSize = in.readCard16();
    short numPorts = in.readCard16();
    short numFormats = in.readCard16();
    byte type = in.readCard8();
    byte[] pad5 = in.readPad(1);
    List<Byte> name = in.readChar(Short.toUnsignedInt(nameSize));
    in.readPadAlign(Short.toUnsignedInt(nameSize));
    List<Format> formats = new ArrayList<>(Short.toUnsignedInt(numFormats));
    for(int i = 0; i < Short.toUnsignedInt(numFormats); i++) {
      formats.add(Format.readFormat(in));
    }
    javaBuilder.baseId(baseId);
    javaBuilder.numPorts(numPorts);
    javaBuilder.type(type);
    javaBuilder.name(name);
    javaBuilder.formats(formats);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(baseId);
    short nameSize = (short) name.size();
    out.writeCard16(nameSize);
    out.writeCard16(numPorts);
    short numFormats = (short) formats.size();
    out.writeCard16(numFormats);
    out.writeCard8(type);
    out.writePad(1);
    out.writeChar(name);
    out.writePadAlign(Short.toUnsignedInt(nameSize));
    for(Format t : formats) {
      t.write(out);
    }
  }

  public boolean isTypeEnabled(@NonNull Type... maskEnums) {
    for(Type m : maskEnums) {
      if(!m.isEnabled(type)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 12 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size()) + XObject.sizeOf(formats);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class AdaptorInfoBuilder {
    public boolean isTypeEnabled(@NonNull Type... maskEnums) {
      for(Type m : maskEnums) {
        if(!m.isEnabled(type)) {
          return false;
        }
      }
      return true;
    }

    public AdaptorInfo.AdaptorInfoBuilder typeEnable(Type... maskEnums) {
      for(Type m : maskEnums) {
        type((byte) m.enableFor(type));
      }
      return this;
    }

    public AdaptorInfo.AdaptorInfoBuilder typeDisable(Type... maskEnums) {
      for(Type m : maskEnums) {
        type((byte) m.disableFor(type));
      }
      return this;
    }

    public int getSize() {
      return 12 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size()) + XObject.sizeOf(formats);
    }
  }
}
