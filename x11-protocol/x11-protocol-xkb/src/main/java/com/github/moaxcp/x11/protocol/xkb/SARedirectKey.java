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
public class SARedirectKey implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte type;

  private byte newkey;

  private byte mask;

  private byte realModifiers;

  private byte vmodsMaskHigh;

  private byte vmodsMaskLow;

  private byte vmodsHigh;

  private byte vmodsLow;

  public static SARedirectKey readSARedirectKey(X11Input in) throws IOException {
    SARedirectKey.SARedirectKeyBuilder javaBuilder = SARedirectKey.builder();
    byte type = in.readCard8();
    byte newkey = in.readCard8();
    byte mask = in.readCard8();
    byte realModifiers = in.readCard8();
    byte vmodsMaskHigh = in.readCard8();
    byte vmodsMaskLow = in.readCard8();
    byte vmodsHigh = in.readCard8();
    byte vmodsLow = in.readCard8();
    javaBuilder.type(type);
    javaBuilder.newkey(newkey);
    javaBuilder.mask(mask);
    javaBuilder.realModifiers(realModifiers);
    javaBuilder.vmodsMaskHigh(vmodsMaskHigh);
    javaBuilder.vmodsMaskLow(vmodsMaskLow);
    javaBuilder.vmodsHigh(vmodsHigh);
    javaBuilder.vmodsLow(vmodsLow);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(newkey);
    out.writeCard8(mask);
    out.writeCard8(realModifiers);
    out.writeCard8(vmodsMaskHigh);
    out.writeCard8(vmodsMaskLow);
    out.writeCard8(vmodsHigh);
    out.writeCard8(vmodsLow);
  }

  public boolean isMaskEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(mask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isRealModifiersEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(realModifiers)) {
        return false;
      }
    }
    return true;
  }

  public boolean isVmodsMaskHighEnabled(@NonNull VModsHigh... maskEnums) {
    for(VModsHigh m : maskEnums) {
      if(!m.isEnabled(vmodsMaskHigh)) {
        return false;
      }
    }
    return true;
  }

  public boolean isVmodsMaskLowEnabled(@NonNull VModsLow... maskEnums) {
    for(VModsLow m : maskEnums) {
      if(!m.isEnabled(vmodsMaskLow)) {
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

  public static class SARedirectKeyBuilder {
    public SARedirectKey.SARedirectKeyBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SARedirectKey.SARedirectKeyBuilder type(byte type) {
      this.type = type;
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

    public SARedirectKey.SARedirectKeyBuilder maskEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mask((byte) m.enableFor(mask));
      }
      return this;
    }

    public SARedirectKey.SARedirectKeyBuilder maskDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mask((byte) m.disableFor(mask));
      }
      return this;
    }

    public boolean isRealModifiersEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(realModifiers)) {
          return false;
        }
      }
      return true;
    }

    public SARedirectKey.SARedirectKeyBuilder realModifiersEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        realModifiers((byte) m.enableFor(realModifiers));
      }
      return this;
    }

    public SARedirectKey.SARedirectKeyBuilder realModifiersDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        realModifiers((byte) m.disableFor(realModifiers));
      }
      return this;
    }

    public boolean isVmodsMaskHighEnabled(@NonNull VModsHigh... maskEnums) {
      for(VModsHigh m : maskEnums) {
        if(!m.isEnabled(vmodsMaskHigh)) {
          return false;
        }
      }
      return true;
    }

    public SARedirectKey.SARedirectKeyBuilder vmodsMaskHighEnable(VModsHigh... maskEnums) {
      for(VModsHigh m : maskEnums) {
        vmodsMaskHigh((byte) m.enableFor(vmodsMaskHigh));
      }
      return this;
    }

    public SARedirectKey.SARedirectKeyBuilder vmodsMaskHighDisable(VModsHigh... maskEnums) {
      for(VModsHigh m : maskEnums) {
        vmodsMaskHigh((byte) m.disableFor(vmodsMaskHigh));
      }
      return this;
    }

    public boolean isVmodsMaskLowEnabled(@NonNull VModsLow... maskEnums) {
      for(VModsLow m : maskEnums) {
        if(!m.isEnabled(vmodsMaskLow)) {
          return false;
        }
      }
      return true;
    }

    public SARedirectKey.SARedirectKeyBuilder vmodsMaskLowEnable(VModsLow... maskEnums) {
      for(VModsLow m : maskEnums) {
        vmodsMaskLow((byte) m.enableFor(vmodsMaskLow));
      }
      return this;
    }

    public SARedirectKey.SARedirectKeyBuilder vmodsMaskLowDisable(VModsLow... maskEnums) {
      for(VModsLow m : maskEnums) {
        vmodsMaskLow((byte) m.disableFor(vmodsMaskLow));
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

    public SARedirectKey.SARedirectKeyBuilder vmodsHighEnable(VModsHigh... maskEnums) {
      for(VModsHigh m : maskEnums) {
        vmodsHigh((byte) m.enableFor(vmodsHigh));
      }
      return this;
    }

    public SARedirectKey.SARedirectKeyBuilder vmodsHighDisable(VModsHigh... maskEnums) {
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

    public SARedirectKey.SARedirectKeyBuilder vmodsLowEnable(VModsLow... maskEnums) {
      for(VModsLow m : maskEnums) {
        vmodsLow((byte) m.enableFor(vmodsLow));
      }
      return this;
    }

    public SARedirectKey.SARedirectKeyBuilder vmodsLowDisable(VModsLow... maskEnums) {
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
