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
public class ModDef implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte mask;

  private byte realMods;

  private short vmods;

  public static ModDef readModDef(X11Input in) throws IOException {
    ModDef.ModDefBuilder javaBuilder = ModDef.builder();
    byte mask = in.readCard8();
    byte realMods = in.readCard8();
    short vmods = in.readCard16();
    javaBuilder.mask(mask);
    javaBuilder.realMods(realMods);
    javaBuilder.vmods(vmods);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(mask);
    out.writeCard8(realMods);
    out.writeCard16(vmods);
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

  public boolean isVmodsEnabled(@NonNull VMod... maskEnums) {
    for(VMod m : maskEnums) {
      if(!m.isEnabled(vmods)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ModDefBuilder {
    public boolean isMaskEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(mask)) {
          return false;
        }
      }
      return true;
    }

    public ModDef.ModDefBuilder maskEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mask((byte) m.enableFor(mask));
      }
      return this;
    }

    public ModDef.ModDefBuilder maskDisable(ModMask... maskEnums) {
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

    public ModDef.ModDefBuilder realModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        realMods((byte) m.enableFor(realMods));
      }
      return this;
    }

    public ModDef.ModDefBuilder realModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        realMods((byte) m.disableFor(realMods));
      }
      return this;
    }

    public boolean isVmodsEnabled(@NonNull VMod... maskEnums) {
      for(VMod m : maskEnums) {
        if(!m.isEnabled(vmods)) {
          return false;
        }
      }
      return true;
    }

    public ModDef.ModDefBuilder vmodsEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        vmods((short) m.enableFor(vmods));
      }
      return this;
    }

    public ModDef.ModDefBuilder vmodsDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        vmods((short) m.disableFor(vmods));
      }
      return this;
    }

    public int getSize() {
      return 4;
    }
  }
}
