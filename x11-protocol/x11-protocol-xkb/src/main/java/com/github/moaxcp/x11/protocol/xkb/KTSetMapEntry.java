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
public class KTSetMapEntry implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte level;

  private byte realMods;

  private short virtualMods;

  public static KTSetMapEntry readKTSetMapEntry(X11Input in) throws IOException {
    KTSetMapEntry.KTSetMapEntryBuilder javaBuilder = KTSetMapEntry.builder();
    byte level = in.readCard8();
    byte realMods = in.readCard8();
    short virtualMods = in.readCard16();
    javaBuilder.level(level);
    javaBuilder.realMods(realMods);
    javaBuilder.virtualMods(virtualMods);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(level);
    out.writeCard8(realMods);
    out.writeCard16(virtualMods);
  }

  public boolean isRealModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(realMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isVirtualModsEnabled(@NonNull VMod... maskEnums) {
    for(VMod m : maskEnums) {
      if(!m.isEnabled(virtualMods)) {
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

  public static class KTSetMapEntryBuilder {
    public boolean isRealModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(realMods)) {
          return false;
        }
      }
      return true;
    }

    public KTSetMapEntry.KTSetMapEntryBuilder realModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        realMods((byte) m.enableFor(realMods));
      }
      return this;
    }

    public KTSetMapEntry.KTSetMapEntryBuilder realModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        realMods((byte) m.disableFor(realMods));
      }
      return this;
    }

    public boolean isVirtualModsEnabled(@NonNull VMod... maskEnums) {
      for(VMod m : maskEnums) {
        if(!m.isEnabled(virtualMods)) {
          return false;
        }
      }
      return true;
    }

    public KTSetMapEntry.KTSetMapEntryBuilder virtualModsEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        virtualMods((short) m.enableFor(virtualMods));
      }
      return this;
    }

    public KTSetMapEntry.KTSetMapEntryBuilder virtualModsDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        virtualMods((short) m.disableFor(virtualMods));
      }
      return this;
    }

    public int getSize() {
      return 4;
    }
  }
}
