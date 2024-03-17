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
public class IndicatorMap implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte flags;

  private byte whichGroups;

  private byte groups;

  private byte whichMods;

  private byte mods;

  private byte realMods;

  private short vmods;

  private int ctrls;

  public static IndicatorMap readIndicatorMap(X11Input in) throws IOException {
    IndicatorMap.IndicatorMapBuilder javaBuilder = IndicatorMap.builder();
    byte flags = in.readCard8();
    byte whichGroups = in.readCard8();
    byte groups = in.readCard8();
    byte whichMods = in.readCard8();
    byte mods = in.readCard8();
    byte realMods = in.readCard8();
    short vmods = in.readCard16();
    int ctrls = in.readCard32();
    javaBuilder.flags(flags);
    javaBuilder.whichGroups(whichGroups);
    javaBuilder.groups(groups);
    javaBuilder.whichMods(whichMods);
    javaBuilder.mods(mods);
    javaBuilder.realMods(realMods);
    javaBuilder.vmods(vmods);
    javaBuilder.ctrls(ctrls);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(flags);
    out.writeCard8(whichGroups);
    out.writeCard8(groups);
    out.writeCard8(whichMods);
    out.writeCard8(mods);
    out.writeCard8(realMods);
    out.writeCard16(vmods);
    out.writeCard32(ctrls);
  }

  public boolean isModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(mods)) {
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

  public boolean isCtrlsEnabled(@NonNull BoolCtrl... maskEnums) {
    for(BoolCtrl m : maskEnums) {
      if(!m.isEnabled(ctrls)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class IndicatorMapBuilder {
    public IndicatorMap.IndicatorMapBuilder flags(IMFlag flags) {
      this.flags = (byte) flags.getValue();
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder flags(byte flags) {
      this.flags = flags;
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder whichGroups(IMGroupsWhich whichGroups) {
      this.whichGroups = (byte) whichGroups.getValue();
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder whichGroups(byte whichGroups) {
      this.whichGroups = whichGroups;
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder groups(SetOfGroup groups) {
      this.groups = (byte) groups.getValue();
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder groups(byte groups) {
      this.groups = groups;
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder whichMods(IMModsWhich whichMods) {
      this.whichMods = (byte) whichMods.getValue();
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder whichMods(byte whichMods) {
      this.whichMods = whichMods;
      return this;
    }

    public boolean isModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(mods)) {
          return false;
        }
      }
      return true;
    }

    public IndicatorMap.IndicatorMapBuilder modsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mods((byte) m.enableFor(mods));
      }
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder modsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mods((byte) m.disableFor(mods));
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

    public IndicatorMap.IndicatorMapBuilder realModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        realMods((byte) m.enableFor(realMods));
      }
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder realModsDisable(ModMask... maskEnums) {
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

    public IndicatorMap.IndicatorMapBuilder vmodsEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        vmods((short) m.enableFor(vmods));
      }
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder vmodsDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        vmods((short) m.disableFor(vmods));
      }
      return this;
    }

    public boolean isCtrlsEnabled(@NonNull BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        if(!m.isEnabled(ctrls)) {
          return false;
        }
      }
      return true;
    }

    public IndicatorMap.IndicatorMapBuilder ctrlsEnable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        ctrls((int) m.enableFor(ctrls));
      }
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder ctrlsDisable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        ctrls((int) m.disableFor(ctrls));
      }
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
