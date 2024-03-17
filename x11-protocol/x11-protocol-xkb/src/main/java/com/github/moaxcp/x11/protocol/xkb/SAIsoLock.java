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
public class SAIsoLock implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte type;

  private byte flags;

  private byte mask;

  private byte realMods;

  private byte group;

  private byte affect;

  private byte vmodsHigh;

  private byte vmodsLow;

  public static SAIsoLock readSAIsoLock(X11Input in) throws IOException {
    SAIsoLock.SAIsoLockBuilder javaBuilder = SAIsoLock.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte mask = in.readCard8();
    byte realMods = in.readCard8();
    byte group = in.readInt8();
    byte affect = in.readCard8();
    byte vmodsHigh = in.readCard8();
    byte vmodsLow = in.readCard8();
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.mask(mask);
    javaBuilder.realMods(realMods);
    javaBuilder.group(group);
    javaBuilder.affect(affect);
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
    out.writeInt8(group);
    out.writeCard8(affect);
    out.writeCard8(vmodsHigh);
    out.writeCard8(vmodsLow);
  }

  public boolean isFlagsEnabled(@NonNull SAIsoLockFlag... maskEnums) {
    for(SAIsoLockFlag m : maskEnums) {
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

  public boolean isAffectEnabled(@NonNull SAIsoLockNoAffect... maskEnums) {
    for(SAIsoLockNoAffect m : maskEnums) {
      if(!m.isEnabled(affect)) {
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

  public static class SAIsoLockBuilder {
    public SAIsoLock.SAIsoLockBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SAIsoLock.SAIsoLockBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public boolean isFlagsEnabled(@NonNull SAIsoLockFlag... maskEnums) {
      for(SAIsoLockFlag m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public SAIsoLock.SAIsoLockBuilder flagsEnable(SAIsoLockFlag... maskEnums) {
      for(SAIsoLockFlag m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public SAIsoLock.SAIsoLockBuilder flagsDisable(SAIsoLockFlag... maskEnums) {
      for(SAIsoLockFlag m : maskEnums) {
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

    public SAIsoLock.SAIsoLockBuilder maskEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mask((byte) m.enableFor(mask));
      }
      return this;
    }

    public SAIsoLock.SAIsoLockBuilder maskDisable(ModMask... maskEnums) {
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

    public SAIsoLock.SAIsoLockBuilder realModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        realMods((byte) m.enableFor(realMods));
      }
      return this;
    }

    public SAIsoLock.SAIsoLockBuilder realModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        realMods((byte) m.disableFor(realMods));
      }
      return this;
    }

    public boolean isAffectEnabled(@NonNull SAIsoLockNoAffect... maskEnums) {
      for(SAIsoLockNoAffect m : maskEnums) {
        if(!m.isEnabled(affect)) {
          return false;
        }
      }
      return true;
    }

    public SAIsoLock.SAIsoLockBuilder affectEnable(SAIsoLockNoAffect... maskEnums) {
      for(SAIsoLockNoAffect m : maskEnums) {
        affect((byte) m.enableFor(affect));
      }
      return this;
    }

    public SAIsoLock.SAIsoLockBuilder affectDisable(SAIsoLockNoAffect... maskEnums) {
      for(SAIsoLockNoAffect m : maskEnums) {
        affect((byte) m.disableFor(affect));
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

    public SAIsoLock.SAIsoLockBuilder vmodsHighEnable(VModsHigh... maskEnums) {
      for(VModsHigh m : maskEnums) {
        vmodsHigh((byte) m.enableFor(vmodsHigh));
      }
      return this;
    }

    public SAIsoLock.SAIsoLockBuilder vmodsHighDisable(VModsHigh... maskEnums) {
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

    public SAIsoLock.SAIsoLockBuilder vmodsLowEnable(VModsLow... maskEnums) {
      for(VModsLow m : maskEnums) {
        vmodsLow((byte) m.enableFor(vmodsLow));
      }
      return this;
    }

    public SAIsoLock.SAIsoLockBuilder vmodsLowDisable(VModsLow... maskEnums) {
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
