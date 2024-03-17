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
public class SetKeyType implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte mask;

  private byte realMods;

  private short virtualMods;

  private byte numLevels;

  private boolean preserve;

  @NonNull
  private List<KTSetMapEntry> entries;

  @NonNull
  private List<KTSetMapEntry> preserveEntries;

  public static SetKeyType readSetKeyType(X11Input in) throws IOException {
    SetKeyType.SetKeyTypeBuilder javaBuilder = SetKeyType.builder();
    byte mask = in.readCard8();
    byte realMods = in.readCard8();
    short virtualMods = in.readCard16();
    byte numLevels = in.readCard8();
    byte nMapEntries = in.readCard8();
    boolean preserve = in.readBool();
    byte[] pad6 = in.readPad(1);
    List<KTSetMapEntry> entries = new ArrayList<>(Byte.toUnsignedInt(nMapEntries));
    for(int i = 0; i < Byte.toUnsignedInt(nMapEntries); i++) {
      entries.add(KTSetMapEntry.readKTSetMapEntry(in));
    }
    List<KTSetMapEntry> preserveEntries = new ArrayList<>((preserve ? 1 : 0) * Byte.toUnsignedInt(nMapEntries));
    for(int i = 0; i < (preserve ? 1 : 0) * Byte.toUnsignedInt(nMapEntries); i++) {
      preserveEntries.add(KTSetMapEntry.readKTSetMapEntry(in));
    }
    javaBuilder.mask(mask);
    javaBuilder.realMods(realMods);
    javaBuilder.virtualMods(virtualMods);
    javaBuilder.numLevels(numLevels);
    javaBuilder.preserve(preserve);
    javaBuilder.entries(entries);
    javaBuilder.preserveEntries(preserveEntries);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(mask);
    out.writeCard8(realMods);
    out.writeCard16(virtualMods);
    out.writeCard8(numLevels);
    byte nMapEntries = (byte) entries.size();
    out.writeCard8(nMapEntries);
    out.writeBool(preserve);
    out.writePad(1);
    for(KTSetMapEntry t : entries) {
      t.write(out);
    }
    for(KTSetMapEntry t : preserveEntries) {
      t.write(out);
    }
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
    return 8 + XObject.sizeOf(entries) + XObject.sizeOf(preserveEntries);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetKeyTypeBuilder {
    public boolean isMaskEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(mask)) {
          return false;
        }
      }
      return true;
    }

    public SetKeyType.SetKeyTypeBuilder maskEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mask((byte) m.enableFor(mask));
      }
      return this;
    }

    public SetKeyType.SetKeyTypeBuilder maskDisable(ModMask... maskEnums) {
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

    public SetKeyType.SetKeyTypeBuilder realModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        realMods((byte) m.enableFor(realMods));
      }
      return this;
    }

    public SetKeyType.SetKeyTypeBuilder realModsDisable(ModMask... maskEnums) {
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

    public SetKeyType.SetKeyTypeBuilder virtualModsEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        virtualMods((short) m.enableFor(virtualMods));
      }
      return this;
    }

    public SetKeyType.SetKeyTypeBuilder virtualModsDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        virtualMods((short) m.disableFor(virtualMods));
      }
      return this;
    }

    public int getSize() {
      return 8 + XObject.sizeOf(entries) + XObject.sizeOf(preserveEntries);
    }
  }
}
