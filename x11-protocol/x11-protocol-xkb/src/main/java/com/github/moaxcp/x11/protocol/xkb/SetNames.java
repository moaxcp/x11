package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.Popcount;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class SetNames implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte OPCODE = 18;

  private short deviceSpec;

  private int which;

  private byte firstType;

  private byte firstKTLevelt;

  private byte nKTLevels;

  private byte firstKey;

  private short totalKTLevelNames;

  private int keycodesName;

  private int geometryName;

  private int symbolsName;

  private int physSymbolsName;

  private int typesName;

  private int compatName;

  @NonNull
  private IntList typeNames;

  @NonNull
  private ByteList nLevelsPerType;

  @NonNull
  private IntList ktLevelNames;

  @NonNull
  private IntList indicatorNames;

  @NonNull
  private IntList virtualModNames;

  @NonNull
  private IntList groups;

  @NonNull
  private ImmutableList<KeyName> keyNames;

  @NonNull
  private ImmutableList<KeyAlias> keyAliases;

  @NonNull
  private IntList radioGroupNames;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetNames readSetNames(X11Input in) throws IOException {
    SetNames.SetNamesBuilder javaBuilder = SetNames.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short virtualMods = in.readCard16();
    int which = in.readCard32();
    byte firstType = in.readCard8();
    byte nTypes = in.readCard8();
    byte firstKTLevelt = in.readCard8();
    byte nKTLevels = in.readCard8();
    int indicators = in.readCard32();
    byte groupNames = in.readCard8();
    byte nRadioGroups = in.readCard8();
    byte firstKey = in.readCard8();
    byte nKeys = in.readCard8();
    byte nKeyAliases = in.readCard8();
    byte[] pad16 = in.readPad(1);
    short totalKTLevelNames = in.readCard16();
    int keycodesName = 0;
    int geometryName = 0;
    int symbolsName = 0;
    int physSymbolsName = 0;
    int typesName = 0;
    int compatName = 0;
    IntList typeNames = null;
    ByteList nLevelsPerType = null;
    in.readPadAlign(Byte.toUnsignedInt(nTypes));
    IntList ktLevelNames = null;
    IntList indicatorNames = null;
    IntList virtualModNames = null;
    IntList groups = null;
    ImmutableList<KeyName> keyNames = null;
    ImmutableList<KeyAlias> keyAliases = null;
    IntList radioGroupNames = null;
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.which(which);
    javaBuilder.firstType(firstType);
    javaBuilder.firstKTLevelt(firstKTLevelt);
    javaBuilder.nKTLevels(nKTLevels);
    javaBuilder.firstKey(firstKey);
    javaBuilder.totalKTLevelNames(totalKTLevelNames);
    if(NameDetail.KEYCODES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      keycodesName = in.readCard32();
      javaBuilder.keycodesName(keycodesName);
    }
    if(NameDetail.GEOMETRY.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      geometryName = in.readCard32();
      javaBuilder.geometryName(geometryName);
    }
    if(NameDetail.SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      symbolsName = in.readCard32();
      javaBuilder.symbolsName(symbolsName);
    }
    if(NameDetail.PHYS_SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      physSymbolsName = in.readCard32();
      javaBuilder.physSymbolsName(physSymbolsName);
    }
    if(NameDetail.TYPES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      typesName = in.readCard32();
      javaBuilder.typesName(typesName);
    }
    if(NameDetail.COMPAT.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      compatName = in.readCard32();
      javaBuilder.compatName(compatName);
    }
    if(NameDetail.KEY_TYPE_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      typeNames = in.readCard32(Byte.toUnsignedInt(nTypes));
      javaBuilder.typeNames(typeNames);
    }
    if(NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      nLevelsPerType = in.readCard8(Byte.toUnsignedInt(nTypes));
      javaBuilder.nLevelsPerType(nLevelsPerType);
    }
    if(NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      ktLevelNames = in.readCard32((int) nLevelsPerType.sum());
      javaBuilder.ktLevelNames(ktLevelNames);
    }
    if(NameDetail.INDICATOR_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      indicatorNames = in.readCard32(Popcount.popcount(Integer.toUnsignedLong(indicators)));
      javaBuilder.indicatorNames(indicatorNames);
    }
    if(NameDetail.VIRTUAL_MOD_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      virtualModNames = in.readCard32(Popcount.popcount(Short.toUnsignedInt(virtualMods)));
      javaBuilder.virtualModNames(virtualModNames);
    }
    if(NameDetail.GROUP_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      groups = in.readCard32(Popcount.popcount(Byte.toUnsignedInt(groupNames)));
      javaBuilder.groups(groups);
    }
    if(NameDetail.KEY_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      keyNames = in.readObject(KeyName::readKeyName, Byte.toUnsignedInt(nKeys));
      javaBuilder.keyNames(keyNames);
    }
    if(NameDetail.KEY_ALIASES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      keyAliases = in.readObject(KeyAlias::readKeyAlias, Byte.toUnsignedInt(nKeyAliases));
      javaBuilder.keyAliases(keyAliases);
    }
    if(NameDetail.R_G_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      radioGroupNames = in.readCard32(Byte.toUnsignedInt(nRadioGroups));
      javaBuilder.radioGroupNames(radioGroupNames);
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    short virtualMods = (short) virtualModNames.size();
    out.writeCard16(virtualMods);
    out.writeCard32(which);
    out.writeCard8(firstType);
    byte nTypes = (byte) nLevelsPerType.size();
    out.writeCard8(nTypes);
    out.writeCard8(firstKTLevelt);
    out.writeCard8(nKTLevels);
    int indicators = indicatorNames.size();
    out.writeCard32(indicators);
    byte groupNames = (byte) groups.size();
    out.writeCard8(groupNames);
    byte nRadioGroups = (byte) radioGroupNames.size();
    out.writeCard8(nRadioGroups);
    out.writeCard8(firstKey);
    byte nKeys = (byte) keyNames.size();
    out.writeCard8(nKeys);
    byte nKeyAliases = (byte) keyAliases.size();
    out.writeCard8(nKeyAliases);
    out.writePad(1);
    out.writeCard16(totalKTLevelNames);
    if(NameDetail.KEYCODES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(keycodesName);
    }
    if(NameDetail.GEOMETRY.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(geometryName);
    }
    if(NameDetail.SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(symbolsName);
    }
    if(NameDetail.PHYS_SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(physSymbolsName);
    }
    if(NameDetail.TYPES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(typesName);
    }
    if(NameDetail.COMPAT.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(compatName);
    }
    if(NameDetail.KEY_TYPE_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(typeNames);
    }
    if(NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard8(nLevelsPerType);
    }
    out.writePadAlign(Byte.toUnsignedInt(nTypes));
    if(NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(ktLevelNames);
    }
    if(NameDetail.INDICATOR_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(indicatorNames);
    }
    if(NameDetail.VIRTUAL_MOD_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(virtualModNames);
    }
    if(NameDetail.GROUP_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(groups);
    }
    if(NameDetail.KEY_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      for(KeyName t : keyNames) {
        t.write(out);
      }
    }
    if(NameDetail.KEY_ALIASES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      for(KeyAlias t : keyAliases) {
        t.write(out);
      }
    }
    if(NameDetail.R_G_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(radioGroupNames);
    }
    out.writePadAlign(getSize());
  }

  public boolean isWhichEnabled(@NonNull NameDetail... maskEnums) {
    for(NameDetail m : maskEnums) {
      if(!m.isEnabled(which)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 28 + (NameDetail.KEYCODES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.GEOMETRY.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.PHYS_SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.TYPES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.COMPAT.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.KEY_TYPE_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * typeNames.size() : 0) + (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 1 * nLevelsPerType.size() : 0) + XObject.getSizeForPadAlign(4, (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 1 * nLevelsPerType.size() : 0)) + (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * ktLevelNames.size() : 0) + (NameDetail.INDICATOR_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * indicatorNames.size() : 0) + (NameDetail.VIRTUAL_MOD_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * virtualModNames.size() : 0) + (NameDetail.GROUP_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * groups.size() : 0) + (NameDetail.KEY_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? XObject.sizeOf(keyNames) : 0) + (NameDetail.KEY_ALIASES.isEnabled((int) (Integer.toUnsignedLong(which))) ? XObject.sizeOf(keyAliases) : 0) + (NameDetail.R_G_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * radioGroupNames.size() : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetNamesBuilder {
    public boolean isWhichEnabled(@NonNull NameDetail... maskEnums) {
      for(NameDetail m : maskEnums) {
        if(!m.isEnabled(which)) {
          return false;
        }
      }
      return true;
    }

    public SetNames.SetNamesBuilder whichEnable(NameDetail... maskEnums) {
      for(NameDetail m : maskEnums) {
        which((int) m.enableFor(which));
      }
      return this;
    }

    public SetNames.SetNamesBuilder whichDisable(NameDetail... maskEnums) {
      for(NameDetail m : maskEnums) {
        which((int) m.disableFor(which));
      }
      return this;
    }

    public SetNames.SetNamesBuilder keycodesName(int keycodesName) {
      this.keycodesName = keycodesName;
      whichEnable(NameDetail.KEYCODES);
      return this;
    }

    public SetNames.SetNamesBuilder geometryName(int geometryName) {
      this.geometryName = geometryName;
      whichEnable(NameDetail.GEOMETRY);
      return this;
    }

    public SetNames.SetNamesBuilder symbolsName(int symbolsName) {
      this.symbolsName = symbolsName;
      whichEnable(NameDetail.SYMBOLS);
      return this;
    }

    public SetNames.SetNamesBuilder physSymbolsName(int physSymbolsName) {
      this.physSymbolsName = physSymbolsName;
      whichEnable(NameDetail.PHYS_SYMBOLS);
      return this;
    }

    public SetNames.SetNamesBuilder typesName(int typesName) {
      this.typesName = typesName;
      whichEnable(NameDetail.TYPES);
      return this;
    }

    public SetNames.SetNamesBuilder compatName(int compatName) {
      this.compatName = compatName;
      whichEnable(NameDetail.COMPAT);
      return this;
    }

    public SetNames.SetNamesBuilder typeNames(IntList typeNames) {
      this.typeNames = typeNames;
      whichEnable(NameDetail.KEY_TYPE_NAMES);
      return this;
    }

    public SetNames.SetNamesBuilder nLevelsPerType(ByteList nLevelsPerType) {
      this.nLevelsPerType = nLevelsPerType;
      whichEnable(NameDetail.K_T_LEVEL_NAMES);
      return this;
    }

    public SetNames.SetNamesBuilder ktLevelNames(IntList ktLevelNames) {
      this.ktLevelNames = ktLevelNames;
      whichEnable(NameDetail.K_T_LEVEL_NAMES);
      return this;
    }

    public SetNames.SetNamesBuilder indicatorNames(IntList indicatorNames) {
      this.indicatorNames = indicatorNames;
      whichEnable(NameDetail.INDICATOR_NAMES);
      return this;
    }

    public SetNames.SetNamesBuilder virtualModNames(IntList virtualModNames) {
      this.virtualModNames = virtualModNames;
      whichEnable(NameDetail.VIRTUAL_MOD_NAMES);
      return this;
    }

    public SetNames.SetNamesBuilder groups(IntList groups) {
      this.groups = groups;
      whichEnable(NameDetail.GROUP_NAMES);
      return this;
    }

    public SetNames.SetNamesBuilder keyNames(ImmutableList<KeyName> keyNames) {
      this.keyNames = keyNames;
      whichEnable(NameDetail.KEY_NAMES);
      return this;
    }

    public SetNames.SetNamesBuilder keyAliases(ImmutableList<KeyAlias> keyAliases) {
      this.keyAliases = keyAliases;
      whichEnable(NameDetail.KEY_ALIASES);
      return this;
    }

    public SetNames.SetNamesBuilder radioGroupNames(IntList radioGroupNames) {
      this.radioGroupNames = radioGroupNames;
      whichEnable(NameDetail.R_G_NAMES);
      return this;
    }

    public int getSize() {
      return 28 + (NameDetail.KEYCODES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.GEOMETRY.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.PHYS_SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.TYPES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.COMPAT.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.KEY_TYPE_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * typeNames.size() : 0) + (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 1 * nLevelsPerType.size() : 0) + XObject.getSizeForPadAlign(4, (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 1 * nLevelsPerType.size() : 0)) + (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * ktLevelNames.size() : 0) + (NameDetail.INDICATOR_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * indicatorNames.size() : 0) + (NameDetail.VIRTUAL_MOD_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * virtualModNames.size() : 0) + (NameDetail.GROUP_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * groups.size() : 0) + (NameDetail.KEY_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? XObject.sizeOf(keyNames) : 0) + (NameDetail.KEY_ALIASES.isEnabled((int) (Integer.toUnsignedLong(which))) ? XObject.sizeOf(keyAliases) : 0) + (NameDetail.R_G_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * radioGroupNames.size() : 0);
    }
  }
}
