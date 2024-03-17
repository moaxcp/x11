package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SASetPtrDflt implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte type;

  private byte flags;

  private byte affect;

  private byte value;

  public static SASetPtrDflt readSASetPtrDflt(X11Input in) throws IOException {
    SASetPtrDflt.SASetPtrDfltBuilder javaBuilder = SASetPtrDflt.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte affect = in.readCard8();
    byte value = in.readInt8();
    byte[] pad4 = in.readPad(4);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.affect(affect);
    javaBuilder.value(value);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writeCard8(affect);
    out.writeInt8(value);
    out.writePad(4);
  }

  public boolean isFlagsEnabled(@NonNull SASetPtrDfltFlag... maskEnums) {
    for(SASetPtrDfltFlag m : maskEnums) {
      if(!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectEnabled(@NonNull SASetPtrDfltFlag... maskEnums) {
    for(SASetPtrDfltFlag m : maskEnums) {
      if(!m.isEnabled(affect)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SASetPtrDfltBuilder {
    public SASetPtrDflt.SASetPtrDfltBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SASetPtrDflt.SASetPtrDfltBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public boolean isFlagsEnabled(@NonNull SASetPtrDfltFlag... maskEnums) {
      for(SASetPtrDfltFlag m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public SASetPtrDflt.SASetPtrDfltBuilder flagsEnable(SASetPtrDfltFlag... maskEnums) {
      for(SASetPtrDfltFlag m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public SASetPtrDflt.SASetPtrDfltBuilder flagsDisable(SASetPtrDfltFlag... maskEnums) {
      for(SASetPtrDfltFlag m : maskEnums) {
        flags((byte) m.disableFor(flags));
      }
      return this;
    }

    public boolean isAffectEnabled(@NonNull SASetPtrDfltFlag... maskEnums) {
      for(SASetPtrDfltFlag m : maskEnums) {
        if(!m.isEnabled(affect)) {
          return false;
        }
      }
      return true;
    }

    public SASetPtrDflt.SASetPtrDfltBuilder affectEnable(SASetPtrDfltFlag... maskEnums) {
      for(SASetPtrDfltFlag m : maskEnums) {
        affect((byte) m.enableFor(affect));
      }
      return this;
    }

    public SASetPtrDflt.SASetPtrDfltBuilder affectDisable(SASetPtrDfltFlag... maskEnums) {
      for(SASetPtrDfltFlag m : maskEnums) {
        affect((byte) m.disableFor(affect));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
