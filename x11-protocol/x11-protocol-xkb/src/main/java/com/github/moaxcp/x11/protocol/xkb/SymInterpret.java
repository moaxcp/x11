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
public class SymInterpret implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private int sym;

  private byte mods;

  private byte match;

  private byte virtualMod;

  private byte flags;

  @NonNull
  private SIAction action;

  public static SymInterpret readSymInterpret(X11Input in) throws IOException {
    SymInterpret.SymInterpretBuilder javaBuilder = SymInterpret.builder();
    int sym = in.readCard32();
    byte mods = in.readCard8();
    byte match = in.readCard8();
    byte virtualMod = in.readCard8();
    byte flags = in.readCard8();
    SIAction action = SIAction.readSIAction(in);
    javaBuilder.sym(sym);
    javaBuilder.mods(mods);
    javaBuilder.match(match);
    javaBuilder.virtualMod(virtualMod);
    javaBuilder.flags(flags);
    javaBuilder.action(action);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(sym);
    out.writeCard8(mods);
    out.writeCard8(match);
    out.writeCard8(virtualMod);
    out.writeCard8(flags);
    action.write(out);
  }

  public boolean isModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(mods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isVirtualModEnabled(@NonNull VModsLow... maskEnums) {
    for(VModsLow m : maskEnums) {
      if(!m.isEnabled(virtualMod)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 8 + action.getSize();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SymInterpretBuilder {
    public boolean isModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(mods)) {
          return false;
        }
      }
      return true;
    }

    public SymInterpret.SymInterpretBuilder modsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mods((byte) m.enableFor(mods));
      }
      return this;
    }

    public SymInterpret.SymInterpretBuilder modsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mods((byte) m.disableFor(mods));
      }
      return this;
    }

    public boolean isVirtualModEnabled(@NonNull VModsLow... maskEnums) {
      for(VModsLow m : maskEnums) {
        if(!m.isEnabled(virtualMod)) {
          return false;
        }
      }
      return true;
    }

    public SymInterpret.SymInterpretBuilder virtualModEnable(VModsLow... maskEnums) {
      for(VModsLow m : maskEnums) {
        virtualMod((byte) m.enableFor(virtualMod));
      }
      return this;
    }

    public SymInterpret.SymInterpretBuilder virtualModDisable(VModsLow... maskEnums) {
      for(VModsLow m : maskEnums) {
        virtualMod((byte) m.disableFor(virtualMod));
      }
      return this;
    }

    public int getSize() {
      return 8 + action.getSize();
    }
  }
}
