package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class KeyType implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte modsMask;

  private byte modsMods;

  private short modsVmods;

  private byte numLevels;

  private boolean hasPreserve;

  @NonNull
  private List<KTMapEntry> map;

  @NonNull
  private List<ModDef> preserve;

  public static KeyType readKeyType(X11Input in) throws IOException {
    KeyType.KeyTypeBuilder javaBuilder = KeyType.builder();
    byte modsMask = in.readCard8();
    byte modsMods = in.readCard8();
    short modsVmods = in.readCard16();
    byte numLevels = in.readCard8();
    byte nMapEntries = in.readCard8();
    boolean hasPreserve = in.readBool();
    byte[] pad6 = in.readPad(1);
    List<KTMapEntry> map = new ArrayList<>(Byte.toUnsignedInt(nMapEntries));
    for(int i = 0; i < Byte.toUnsignedInt(nMapEntries); i++) {
      map.add(KTMapEntry.readKTMapEntry(in));
    }
    List<ModDef> preserve = new ArrayList<>((hasPreserve ? 1 : 0) * Byte.toUnsignedInt(nMapEntries));
    for(int i = 0; i < (hasPreserve ? 1 : 0) * Byte.toUnsignedInt(nMapEntries); i++) {
      preserve.add(ModDef.readModDef(in));
    }
    javaBuilder.modsMask(modsMask);
    javaBuilder.modsMods(modsMods);
    javaBuilder.modsVmods(modsVmods);
    javaBuilder.numLevels(numLevels);
    javaBuilder.hasPreserve(hasPreserve);
    javaBuilder.map(map);
    javaBuilder.preserve(preserve);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(modsMask);
    out.writeCard8(modsMods);
    out.writeCard16(modsVmods);
    out.writeCard8(numLevels);
    byte nMapEntries = (byte) map.size();
    out.writeCard8(nMapEntries);
    out.writeBool(hasPreserve);
    out.writePad(1);
    for(KTMapEntry t : map) {
      t.write(out);
    }
    for(ModDef t : preserve) {
      t.write(out);
    }
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
    return 8 + XObject.sizeOf(map) + XObject.sizeOf(preserve);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class KeyTypeBuilder {
    public boolean isModsMaskEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(modsMask)) {
          return false;
        }
      }
      return true;
    }

    public KeyType.KeyTypeBuilder modsMaskEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        modsMask((byte) m.enableFor(modsMask));
      }
      return this;
    }

    public KeyType.KeyTypeBuilder modsMaskDisable(ModMask... maskEnums) {
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

    public KeyType.KeyTypeBuilder modsModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        modsMods((byte) m.enableFor(modsMods));
      }
      return this;
    }

    public KeyType.KeyTypeBuilder modsModsDisable(ModMask... maskEnums) {
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

    public KeyType.KeyTypeBuilder modsVmodsEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        modsVmods((short) m.enableFor(modsVmods));
      }
      return this;
    }

    public KeyType.KeyTypeBuilder modsVmodsDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        modsVmods((short) m.disableFor(modsVmods));
      }
      return this;
    }

    public int getSize() {
      return 8 + XObject.sizeOf(map) + XObject.sizeOf(preserve);
    }
  }
}
