package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.Popcount;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetMap implements OneWayRequest, XkbObject {
  public static final byte OPCODE = 9;

  private short deviceSpec;

  private short present;

  private short flags;

  private byte minKeyCode;

  private byte maxKeyCode;

  private byte firstType;

  private byte firstKeySym;

  private short totalSyms;

  private byte firstKeyAction;

  private byte firstKeyBehavior;

  private byte nKeyBehaviors;

  private byte firstKeyExplicit;

  private byte nKeyExplicit;

  private byte firstModMapKey;

  private byte nModMapKeys;

  private byte firstVModMapKey;

  private byte nVModMapKeys;

  @NonNull
  private List<SetKeyType> types;

  @NonNull
  private List<KeySymMap> syms;

  @NonNull
  private List<Byte> actionsCount;

  @NonNull
  private List<ActionUnion> actions;

  @NonNull
  private List<SetBehavior> behaviors;

  @NonNull
  private List<Byte> vmods;

  @NonNull
  private List<SetExplicit> explicit;

  @NonNull
  private List<KeyModMap> modmap;

  @NonNull
  private List<KeyVModMap> vmodmap;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetMap readSetMap(X11Input in) throws IOException {
    SetMap.SetMapBuilder javaBuilder = SetMap.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short present = in.readCard16();
    short flags = in.readCard16();
    byte minKeyCode = in.readCard8();
    byte maxKeyCode = in.readCard8();
    byte firstType = in.readCard8();
    byte nTypes = in.readCard8();
    byte firstKeySym = in.readCard8();
    byte nKeySyms = in.readCard8();
    short totalSyms = in.readCard16();
    byte firstKeyAction = in.readCard8();
    byte nKeyActions = in.readCard8();
    short totalActions = in.readCard16();
    byte firstKeyBehavior = in.readCard8();
    byte nKeyBehaviors = in.readCard8();
    byte totalKeyBehaviors = in.readCard8();
    byte firstKeyExplicit = in.readCard8();
    byte nKeyExplicit = in.readCard8();
    byte totalKeyExplicit = in.readCard8();
    byte firstModMapKey = in.readCard8();
    byte nModMapKeys = in.readCard8();
    byte totalModMapKeys = in.readCard8();
    byte firstVModMapKey = in.readCard8();
    byte nVModMapKeys = in.readCard8();
    byte totalVModMapKeys = in.readCard8();
    short virtualMods = in.readCard16();
    List<SetKeyType> types = null;
    List<KeySymMap> syms = null;
    List<Byte> actionsCount = null;
    in.readPadAlign(Byte.toUnsignedInt(nKeyActions));
    List<ActionUnion> actions = null;
    List<SetBehavior> behaviors = null;
    List<Byte> vmods = null;
    in.readPadAlign(com.github.moaxcp.x11client.protocol.Popcount.popcount(Short.toUnsignedInt(virtualMods)));
    List<SetExplicit> explicit = null;
    List<KeyModMap> modmap = null;
    List<KeyVModMap> vmodmap = null;
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.present(present);
    javaBuilder.flags(flags);
    javaBuilder.minKeyCode(minKeyCode);
    javaBuilder.maxKeyCode(maxKeyCode);
    javaBuilder.firstType(firstType);
    javaBuilder.firstKeySym(firstKeySym);
    javaBuilder.totalSyms(totalSyms);
    javaBuilder.firstKeyAction(firstKeyAction);
    javaBuilder.firstKeyBehavior(firstKeyBehavior);
    javaBuilder.nKeyBehaviors(nKeyBehaviors);
    javaBuilder.firstKeyExplicit(firstKeyExplicit);
    javaBuilder.nKeyExplicit(nKeyExplicit);
    javaBuilder.firstModMapKey(firstModMapKey);
    javaBuilder.nModMapKeys(nModMapKeys);
    javaBuilder.firstVModMapKey(firstVModMapKey);
    javaBuilder.nVModMapKeys(nVModMapKeys);
    if(MapPart.KEY_TYPES.isEnabled(Short.toUnsignedInt(present))) {
      types = in.readObject(SetKeyType::readSetKeyType, Byte.toUnsignedInt(nTypes));
      javaBuilder.types(types);
    }
    if(MapPart.KEY_SYMS.isEnabled(Short.toUnsignedInt(present))) {
      syms = in.readObject(KeySymMap::readKeySymMap, Byte.toUnsignedInt(nKeySyms));
      javaBuilder.syms(syms);
    }
    if(MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present))) {
      actionsCount = in.readCard8(Byte.toUnsignedInt(nKeyActions));
      javaBuilder.actionsCount(actionsCount);
    }
    if(MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present))) {
      actions = in.readObject(ActionUnion::readActionUnion, Short.toUnsignedInt(totalActions));
      javaBuilder.actions(actions);
    }
    if(MapPart.KEY_BEHAVIORS.isEnabled(Short.toUnsignedInt(present))) {
      behaviors = in.readObject(SetBehavior::readSetBehavior, Byte.toUnsignedInt(totalKeyBehaviors));
      javaBuilder.behaviors(behaviors);
    }
    if(MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present))) {
      vmods = in.readCard8(Popcount.popcount(Short.toUnsignedInt(virtualMods)));
      javaBuilder.vmods(vmods);
    }
    if(MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present))) {
      explicit = in.readObject(SetExplicit::readSetExplicit, Byte.toUnsignedInt(totalKeyExplicit));
      javaBuilder.explicit(explicit);
    }
    if(MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present))) {
      modmap = in.readObject(KeyModMap::readKeyModMap, Byte.toUnsignedInt(totalModMapKeys));
      javaBuilder.modmap(modmap);
    }
    if(MapPart.VIRTUAL_MOD_MAP.isEnabled(Short.toUnsignedInt(present))) {
      vmodmap = in.readObject(KeyVModMap::readKeyVModMap, Byte.toUnsignedInt(totalVModMapKeys));
      javaBuilder.vmodmap(vmodmap);
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard16(present);
    out.writeCard16(flags);
    out.writeCard8(minKeyCode);
    out.writeCard8(maxKeyCode);
    out.writeCard8(firstType);
    byte nTypes = (byte) types.size();
    out.writeCard8(nTypes);
    out.writeCard8(firstKeySym);
    byte nKeySyms = (byte) syms.size();
    out.writeCard8(nKeySyms);
    out.writeCard16(totalSyms);
    out.writeCard8(firstKeyAction);
    byte nKeyActions = (byte) actionsCount.size();
    out.writeCard8(nKeyActions);
    short totalActions = (short) actions.size();
    out.writeCard16(totalActions);
    out.writeCard8(firstKeyBehavior);
    out.writeCard8(nKeyBehaviors);
    byte totalKeyBehaviors = (byte) behaviors.size();
    out.writeCard8(totalKeyBehaviors);
    out.writeCard8(firstKeyExplicit);
    out.writeCard8(nKeyExplicit);
    byte totalKeyExplicit = (byte) explicit.size();
    out.writeCard8(totalKeyExplicit);
    out.writeCard8(firstModMapKey);
    out.writeCard8(nModMapKeys);
    byte totalModMapKeys = (byte) modmap.size();
    out.writeCard8(totalModMapKeys);
    out.writeCard8(firstVModMapKey);
    out.writeCard8(nVModMapKeys);
    byte totalVModMapKeys = (byte) vmodmap.size();
    out.writeCard8(totalVModMapKeys);
    short virtualMods = (short) vmods.size();
    out.writeCard16(virtualMods);
    if(MapPart.KEY_TYPES.isEnabled(Short.toUnsignedInt(present))) {
      for(SetKeyType t : types) {
        t.write(out);
      }
    }
    if(MapPart.KEY_SYMS.isEnabled(Short.toUnsignedInt(present))) {
      for(KeySymMap t : syms) {
        t.write(out);
      }
    }
    if(MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present))) {
      out.writeCard8(actionsCount);
    }
    out.writePadAlign(Byte.toUnsignedInt(nKeyActions));
    if(MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present))) {
      for(ActionUnion t : actions) {
        t.write(out);
      }
    }
    if(MapPart.KEY_BEHAVIORS.isEnabled(Short.toUnsignedInt(present))) {
      for(SetBehavior t : behaviors) {
        t.write(out);
      }
    }
    if(MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present))) {
      out.writeCard8(vmods);
    }
    out.writePadAlign(com.github.moaxcp.x11client.protocol.Popcount.popcount(Short.toUnsignedInt(virtualMods)));
    if(MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present))) {
      for(SetExplicit t : explicit) {
        t.write(out);
      }
    }
    if(MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present))) {
      for(KeyModMap t : modmap) {
        t.write(out);
      }
    }
    if(MapPart.VIRTUAL_MOD_MAP.isEnabled(Short.toUnsignedInt(present))) {
      for(KeyVModMap t : vmodmap) {
        t.write(out);
      }
    }
    out.writePadAlign(getSize());
  }

  public boolean isPresentEnabled(@NonNull MapPart... maskEnums) {
    for(MapPart m : maskEnums) {
      if(!m.isEnabled(present)) {
        return false;
      }
    }
    return true;
  }

  public boolean isFlagsEnabled(@NonNull SetMapFlags... maskEnums) {
    for(SetMapFlags m : maskEnums) {
      if(!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 36 + (MapPart.KEY_TYPES.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(types) : 0) + (MapPart.KEY_SYMS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(syms) : 0) + (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? 1 * actionsCount.size() : 0) + XObject.getSizeForPadAlign(4, (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? 1 * actionsCount.size() : 0)) + (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(actions) : 0) + (MapPart.KEY_BEHAVIORS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(behaviors) : 0) + (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present)) ? 1 * vmods.size() : 0) + XObject.getSizeForPadAlign(4, (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present)) ? 1 * vmods.size() : 0)) + (MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(explicit) : 0) + (MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(modmap) : 0) + (MapPart.VIRTUAL_MOD_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(vmodmap) : 0);
  }

  public static class SetMapBuilder {
    public boolean isPresentEnabled(@NonNull MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        if(!m.isEnabled(present)) {
          return false;
        }
      }
      return true;
    }

    public SetMap.SetMapBuilder presentEnable(MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        present((short) m.enableFor(present));
      }
      return this;
    }

    public SetMap.SetMapBuilder presentDisable(MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        present((short) m.disableFor(present));
      }
      return this;
    }

    public boolean isFlagsEnabled(@NonNull SetMapFlags... maskEnums) {
      for(SetMapFlags m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public SetMap.SetMapBuilder flagsEnable(SetMapFlags... maskEnums) {
      for(SetMapFlags m : maskEnums) {
        flags((short) m.enableFor(flags));
      }
      return this;
    }

    public SetMap.SetMapBuilder flagsDisable(SetMapFlags... maskEnums) {
      for(SetMapFlags m : maskEnums) {
        flags((short) m.disableFor(flags));
      }
      return this;
    }

    public SetMap.SetMapBuilder types(List<SetKeyType> types) {
      this.types = types;
      presentEnable(MapPart.KEY_TYPES);
      return this;
    }

    public SetMap.SetMapBuilder syms(List<KeySymMap> syms) {
      this.syms = syms;
      presentEnable(MapPart.KEY_SYMS);
      return this;
    }

    public SetMap.SetMapBuilder actionsCount(List<Byte> actionsCount) {
      this.actionsCount = actionsCount;
      presentEnable(MapPart.KEY_ACTIONS);
      return this;
    }

    public SetMap.SetMapBuilder actions(List<ActionUnion> actions) {
      this.actions = actions;
      presentEnable(MapPart.KEY_ACTIONS);
      return this;
    }

    public SetMap.SetMapBuilder behaviors(List<SetBehavior> behaviors) {
      this.behaviors = behaviors;
      presentEnable(MapPart.KEY_BEHAVIORS);
      return this;
    }

    public SetMap.SetMapBuilder vmods(List<Byte> vmods) {
      this.vmods = vmods;
      presentEnable(MapPart.VIRTUAL_MODS);
      return this;
    }

    public SetMap.SetMapBuilder explicit(List<SetExplicit> explicit) {
      this.explicit = explicit;
      presentEnable(MapPart.EXPLICIT_COMPONENTS);
      return this;
    }

    public SetMap.SetMapBuilder modmap(List<KeyModMap> modmap) {
      this.modmap = modmap;
      presentEnable(MapPart.MODIFIER_MAP);
      return this;
    }

    public SetMap.SetMapBuilder vmodmap(List<KeyVModMap> vmodmap) {
      this.vmodmap = vmodmap;
      presentEnable(MapPart.VIRTUAL_MOD_MAP);
      return this;
    }

    public int getSize() {
      return 36 + (MapPart.KEY_TYPES.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(types) : 0) + (MapPart.KEY_SYMS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(syms) : 0) + (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? 1 * actionsCount.size() : 0) + XObject.getSizeForPadAlign(4, (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? 1 * actionsCount.size() : 0)) + (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(actions) : 0) + (MapPart.KEY_BEHAVIORS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(behaviors) : 0) + (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present)) ? 1 * vmods.size() : 0) + XObject.getSizeForPadAlign(4, (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present)) ? 1 * vmods.size() : 0)) + (MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(explicit) : 0) + (MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(modmap) : 0) + (MapPart.VIRTUAL_MOD_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(vmodmap) : 0);
    }
  }
}
