package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SASetMods implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte type;

  private byte flags;

  private byte mask;

  private byte realMods;

  private byte vmodsHigh;

  private byte vmodsLow;

  public static SASetMods readSASetMods(X11Input in) throws IOException {
    SASetMods.SASetModsBuilder javaBuilder = SASetMods.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte mask = in.readCard8();
    byte realMods = in.readCard8();
    byte vmodsHigh = in.readCard8();
    byte vmodsLow = in.readCard8();
    byte[] pad6 = in.readPad(2);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.mask(mask);
    javaBuilder.realMods(realMods);
    javaBuilder.vmodsHigh(vmodsHigh);
    javaBuilder.vmodsLow(vmodsLow);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writeCard8(mask);
    out.writeCard8(realMods);
    out.writeCard8(vmodsHigh);
    out.writeCard8(vmodsLow);
    out.writePad(2);
  }

  public boolean isFlagsEnabled(@NonNull Sa... maskEnums) {
    for(Sa m : maskEnums) {
      if(!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  public boolean isMaskEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(mask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isRealModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(realMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isVmodsHighEnabled(@NonNull VModsHigh... maskEnums) {
    for(VModsHigh m : maskEnums) {
      if(!m.isEnabled(vmodsHigh)) {
        return false;
      }
    }
    return true;
  }

  public boolean isVmodsLowEnabled(@NonNull VModsLow... maskEnums) {
    for(VModsLow m : maskEnums) {
      if(!m.isEnabled(vmodsLow)) {
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

  public static class SASetModsBuilder {
    public SASetMods.SASetModsBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SASetMods.SASetModsBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public boolean isFlagsEnabled(@NonNull Sa... maskEnums) {
      for(Sa m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public SASetMods.SASetModsBuilder flagsEnable(Sa... maskEnums) {
      for(Sa m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public SASetMods.SASetModsBuilder flagsDisable(Sa... maskEnums) {
      for(Sa m : maskEnums) {
        flags((byte) m.disableFor(flags));
      }
      return this;
    }

    public boolean isMaskEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(mask)) {
          return false;
        }
      }
      return true;
    }

    public SASetMods.SASetModsBuilder maskEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mask((byte) m.enableFor(mask));
      }
      return this;
    }

    public SASetMods.SASetModsBuilder maskDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mask((byte) m.disableFor(mask));
      }
      return this;
    }

    public boolean isRealModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(realMods)) {
          return false;
        }
      }
      return true;
    }

    public SASetMods.SASetModsBuilder realModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        realMods((byte) m.enableFor(realMods));
      }
      return this;
    }

    public SASetMods.SASetModsBuilder realModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        realMods((byte) m.disableFor(realMods));
      }
      return this;
    }

    public boolean isVmodsHighEnabled(@NonNull VModsHigh... maskEnums) {
      for(VModsHigh m : maskEnums) {
        if(!m.isEnabled(vmodsHigh)) {
          return false;
        }
      }
      return true;
    }

    public SASetMods.SASetModsBuilder vmodsHighEnable(VModsHigh... maskEnums) {
      for(VModsHigh m : maskEnums) {
        vmodsHigh((byte) m.enableFor(vmodsHigh));
      }
      return this;
    }

    public SASetMods.SASetModsBuilder vmodsHighDisable(VModsHigh... maskEnums) {
      for(VModsHigh m : maskEnums) {
        vmodsHigh((byte) m.disableFor(vmodsHigh));
      }
      return this;
    }

    public boolean isVmodsLowEnabled(@NonNull VModsLow... maskEnums) {
      for(VModsLow m : maskEnums) {
        if(!m.isEnabled(vmodsLow)) {
          return false;
        }
      }
      return true;
    }

    public SASetMods.SASetModsBuilder vmodsLowEnable(VModsLow... maskEnums) {
      for(VModsLow m : maskEnums) {
        vmodsLow((byte) m.enableFor(vmodsLow));
      }
      return this;
    }

    public SASetMods.SASetModsBuilder vmodsLowDisable(VModsLow... maskEnums) {
      for(VModsLow m : maskEnums) {
        vmodsLow((byte) m.disableFor(vmodsLow));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
