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
public class KTMapEntry implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private boolean active;

  private byte modsMask;

  private byte level;

  private byte modsMods;

  private short modsVmods;

  public static KTMapEntry readKTMapEntry(X11Input in) throws IOException {
    KTMapEntry.KTMapEntryBuilder javaBuilder = KTMapEntry.builder();
    boolean active = in.readBool();
    byte modsMask = in.readCard8();
    byte level = in.readCard8();
    byte modsMods = in.readCard8();
    short modsVmods = in.readCard16();
    byte[] pad5 = in.readPad(2);
    javaBuilder.active(active);
    javaBuilder.modsMask(modsMask);
    javaBuilder.level(level);
    javaBuilder.modsMods(modsMods);
    javaBuilder.modsVmods(modsVmods);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeBool(active);
    out.writeCard8(modsMask);
    out.writeCard8(level);
    out.writeCard8(modsMods);
    out.writeCard16(modsVmods);
    out.writePad(2);
  }

  public boolean isModsMaskEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(modsMask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isModsModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(modsMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isModsVmodsEnabled(@NonNull VMod... maskEnums) {
    for(VMod m : maskEnums) {
      if(!m.isEnabled(modsVmods)) {
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

  public static class KTMapEntryBuilder {
    public boolean isModsMaskEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(modsMask)) {
          return false;
        }
      }
      return true;
    }

    public KTMapEntry.KTMapEntryBuilder modsMaskEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        modsMask((byte) m.enableFor(modsMask));
      }
      return this;
    }

    public KTMapEntry.KTMapEntryBuilder modsMaskDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        modsMask((byte) m.disableFor(modsMask));
      }
      return this;
    }

    public boolean isModsModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(modsMods)) {
          return false;
        }
      }
      return true;
    }

    public KTMapEntry.KTMapEntryBuilder modsModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        modsMods((byte) m.enableFor(modsMods));
      }
      return this;
    }

    public KTMapEntry.KTMapEntryBuilder modsModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        modsMods((byte) m.disableFor(modsMods));
      }
      return this;
    }

    public boolean isModsVmodsEnabled(@NonNull VMod... maskEnums) {
      for(VMod m : maskEnums) {
        if(!m.isEnabled(modsVmods)) {
          return false;
        }
      }
      return true;
    }

    public KTMapEntry.KTMapEntryBuilder modsVmodsEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        modsVmods((short) m.enableFor(modsVmods));
      }
      return this;
    }

    public KTMapEntry.KTMapEntryBuilder modsVmodsDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        modsVmods((short) m.disableFor(modsVmods));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
