package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.Popcount;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetMapReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";

  private byte deviceID;

  private short sequenceNumber;

  private byte minKeyCode;

  private byte maxKeyCode;

  private short present;

  private byte firstType;

  private byte totalTypes;

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
  private List<KeyType> typesRtrn;

  @NonNull
  private List<KeySymMap> symsRtrn;

  @NonNull
  private List<Byte> actsRtrnCount;

  @NonNull
  private List<ActionUnion> actsRtrnActs;

  @NonNull
  private List<SetBehavior> behaviorsRtrn;

  @NonNull
  private List<Byte> vmodsRtrn;

  @NonNull
  private List<SetExplicit> explicitRtrn;

  @NonNull
  private List<KeyModMap> modmapRtrn;

  @NonNull
  private List<KeyVModMap> vmodmapRtrn;

  public static GetMapReply readGetMapReply(byte deviceID, short sequenceNumber, X11Input in) throws
      IOException {
    GetMapReply.GetMapReplyBuilder javaBuilder = GetMapReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(2);
    byte minKeyCode = in.readCard8();
    byte maxKeyCode = in.readCard8();
    short present = in.readCard16();
    byte firstType = in.readCard8();
    byte nTypes = in.readCard8();
    byte totalTypes = in.readCard8();
    byte firstKeySym = in.readCard8();
    short totalSyms = in.readCard16();
    byte nKeySyms = in.readCard8();
    byte firstKeyAction = in.readCard8();
    short totalActions = in.readCard16();
    byte nKeyActions = in.readCard8();
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
    byte[] pad29 = in.readPad(1);
    short virtualMods = in.readCard16();
    List<KeyType> typesRtrn = null;
    List<KeySymMap> symsRtrn = null;
    List<Byte> actsRtrnCount = null;
    in.readPadAlign(Byte.toUnsignedInt(nKeyActions));
    List<ActionUnion> actsRtrnActs = null;
    List<SetBehavior> behaviorsRtrn = null;
    List<Byte> vmodsRtrn = null;
    in.readPadAlign(com.github.moaxcp.x11.protocol.Popcount.popcount(Short.toUnsignedInt(virtualMods)));
    List<SetExplicit> explicitRtrn = null;
    in.readPadAlign(Byte.toUnsignedInt(totalKeyExplicit));
    List<KeyModMap> modmapRtrn = null;
    in.readPadAlign(Byte.toUnsignedInt(totalModMapKeys));
    List<KeyVModMap> vmodmapRtrn = null;
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.minKeyCode(minKeyCode);
    javaBuilder.maxKeyCode(maxKeyCode);
    javaBuilder.present(present);
    javaBuilder.firstType(firstType);
    javaBuilder.totalTypes(totalTypes);
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
      typesRtrn = in.readObject(KeyType::readKeyType, Byte.toUnsignedInt(nTypes));
      javaBuilder.typesRtrn(typesRtrn);
    }
    if(MapPart.KEY_SYMS.isEnabled(Short.toUnsignedInt(present))) {
      symsRtrn = in.readObject(KeySymMap::readKeySymMap, Byte.toUnsignedInt(nKeySyms));
      javaBuilder.symsRtrn(symsRtrn);
    }
    if(MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present))) {
      actsRtrnCount = in.readCard8(Byte.toUnsignedInt(nKeyActions));
      javaBuilder.actsRtrnCount(actsRtrnCount);
    }
    if(MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present))) {
      actsRtrnActs = in.readObject(ActionUnion::readActionUnion, Short.toUnsignedInt(totalActions));
      javaBuilder.actsRtrnActs(actsRtrnActs);
    }
    if(MapPart.KEY_BEHAVIORS.isEnabled(Short.toUnsignedInt(present))) {
      behaviorsRtrn = in.readObject(SetBehavior::readSetBehavior, Byte.toUnsignedInt(totalKeyBehaviors));
      javaBuilder.behaviorsRtrn(behaviorsRtrn);
    }
    if(MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present))) {
      vmodsRtrn = in.readCard8(Popcount.popcount(Short.toUnsignedInt(virtualMods)));
      javaBuilder.vmodsRtrn(vmodsRtrn);
    }
    if(MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present))) {
      explicitRtrn = in.readObject(SetExplicit::readSetExplicit, Byte.toUnsignedInt(totalKeyExplicit));
      javaBuilder.explicitRtrn(explicitRtrn);
    }
    if(MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present))) {
      modmapRtrn = in.readObject(KeyModMap::readKeyModMap, Byte.toUnsignedInt(totalModMapKeys));
      javaBuilder.modmapRtrn(modmapRtrn);
    }
    if(MapPart.VIRTUAL_MOD_MAP.isEnabled(Short.toUnsignedInt(present))) {
      vmodmapRtrn = in.readObject(KeyVModMap::readKeyVModMap, Byte.toUnsignedInt(totalVModMapKeys));
      javaBuilder.vmodmapRtrn(vmodmapRtrn);
    }
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(deviceID);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writePad(2);
    out.writeCard8(minKeyCode);
    out.writeCard8(maxKeyCode);
    out.writeCard16(present);
    out.writeCard8(firstType);
    byte nTypes = (byte) typesRtrn.size();
    out.writeCard8(nTypes);
    out.writeCard8(totalTypes);
    out.writeCard8(firstKeySym);
    out.writeCard16(totalSyms);
    byte nKeySyms = (byte) symsRtrn.size();
    out.writeCard8(nKeySyms);
    out.writeCard8(firstKeyAction);
    short totalActions = (short) actsRtrnActs.size();
    out.writeCard16(totalActions);
    byte nKeyActions = (byte) actsRtrnCount.size();
    out.writeCard8(nKeyActions);
    out.writeCard8(firstKeyBehavior);
    out.writeCard8(nKeyBehaviors);
    byte totalKeyBehaviors = (byte) behaviorsRtrn.size();
    out.writeCard8(totalKeyBehaviors);
    out.writeCard8(firstKeyExplicit);
    out.writeCard8(nKeyExplicit);
    byte totalKeyExplicit = (byte) explicitRtrn.size();
    out.writeCard8(totalKeyExplicit);
    out.writeCard8(firstModMapKey);
    out.writeCard8(nModMapKeys);
    byte totalModMapKeys = (byte) modmapRtrn.size();
    out.writeCard8(totalModMapKeys);
    out.writeCard8(firstVModMapKey);
    out.writeCard8(nVModMapKeys);
    byte totalVModMapKeys = (byte) vmodmapRtrn.size();
    out.writeCard8(totalVModMapKeys);
    out.writePad(1);
    short virtualMods = (short) vmodsRtrn.size();
    out.writeCard16(virtualMods);
    if(MapPart.KEY_TYPES.isEnabled(Short.toUnsignedInt(present))) {
      for(KeyType t : typesRtrn) {
        t.write(out);
      }
    }
    if(MapPart.KEY_SYMS.isEnabled(Short.toUnsignedInt(present))) {
      for(KeySymMap t : symsRtrn) {
        t.write(out);
      }
    }
    if(MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present))) {
      out.writeCard8(actsRtrnCount);
    }
    out.writePadAlign(Byte.toUnsignedInt(nKeyActions));
    if(MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present))) {
      for(ActionUnion t : actsRtrnActs) {
        t.write(out);
      }
    }
    if(MapPart.KEY_BEHAVIORS.isEnabled(Short.toUnsignedInt(present))) {
      for(SetBehavior t : behaviorsRtrn) {
        t.write(out);
      }
    }
    if(MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present))) {
      out.writeCard8(vmodsRtrn);
    }
    out.writePadAlign(com.github.moaxcp.x11.protocol.Popcount.popcount(Short.toUnsignedInt(virtualMods)));
    if(MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present))) {
      for(SetExplicit t : explicitRtrn) {
        t.write(out);
      }
    }
    out.writePadAlign(Byte.toUnsignedInt(totalKeyExplicit));
    if(MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present))) {
      for(KeyModMap t : modmapRtrn) {
        t.write(out);
      }
    }
    out.writePadAlign(Byte.toUnsignedInt(totalModMapKeys));
    if(MapPart.VIRTUAL_MOD_MAP.isEnabled(Short.toUnsignedInt(present))) {
      for(KeyVModMap t : vmodmapRtrn) {
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

  @Override
  public int getSize() {
    return 40 + (MapPart.KEY_TYPES.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(typesRtrn) : 0) + (MapPart.KEY_SYMS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(symsRtrn) : 0) + (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? 1 * actsRtrnCount.size() : 0) + XObject.getSizeForPadAlign(4, (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? 1 * actsRtrnCount.size() : 0)) + (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(actsRtrnActs) : 0) + (MapPart.KEY_BEHAVIORS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(behaviorsRtrn) : 0) + (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present)) ? 1 * vmodsRtrn.size() : 0) + XObject.getSizeForPadAlign(4, (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present)) ? 1 * vmodsRtrn.size() : 0)) + (MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(explicitRtrn) : 0) + XObject.getSizeForPadAlign(4, (MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(explicitRtrn) : 0)) + (MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(modmapRtrn) : 0) + XObject.getSizeForPadAlign(4, (MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(modmapRtrn) : 0)) + (MapPart.VIRTUAL_MOD_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(vmodmapRtrn) : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetMapReplyBuilder {
    public boolean isPresentEnabled(@NonNull MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        if(!m.isEnabled(present)) {
          return false;
        }
      }
      return true;
    }

    public GetMapReply.GetMapReplyBuilder presentEnable(MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        present((short) m.enableFor(present));
      }
      return this;
    }

    public GetMapReply.GetMapReplyBuilder presentDisable(MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        present((short) m.disableFor(present));
      }
      return this;
    }

    public GetMapReply.GetMapReplyBuilder typesRtrn(List<KeyType> typesRtrn) {
      this.typesRtrn = typesRtrn;
      presentEnable(MapPart.KEY_TYPES);
      return this;
    }

    public GetMapReply.GetMapReplyBuilder symsRtrn(List<KeySymMap> symsRtrn) {
      this.symsRtrn = symsRtrn;
      presentEnable(MapPart.KEY_SYMS);
      return this;
    }

    public GetMapReply.GetMapReplyBuilder actsRtrnCount(List<Byte> actsRtrnCount) {
      this.actsRtrnCount = actsRtrnCount;
      presentEnable(MapPart.KEY_ACTIONS);
      return this;
    }

    public GetMapReply.GetMapReplyBuilder actsRtrnActs(List<ActionUnion> actsRtrnActs) {
      this.actsRtrnActs = actsRtrnActs;
      presentEnable(MapPart.KEY_ACTIONS);
      return this;
    }

    public GetMapReply.GetMapReplyBuilder behaviorsRtrn(List<SetBehavior> behaviorsRtrn) {
      this.behaviorsRtrn = behaviorsRtrn;
      presentEnable(MapPart.KEY_BEHAVIORS);
      return this;
    }

    public GetMapReply.GetMapReplyBuilder vmodsRtrn(List<Byte> vmodsRtrn) {
      this.vmodsRtrn = vmodsRtrn;
      presentEnable(MapPart.VIRTUAL_MODS);
      return this;
    }

    public GetMapReply.GetMapReplyBuilder explicitRtrn(List<SetExplicit> explicitRtrn) {
      this.explicitRtrn = explicitRtrn;
      presentEnable(MapPart.EXPLICIT_COMPONENTS);
      return this;
    }

    public GetMapReply.GetMapReplyBuilder modmapRtrn(List<KeyModMap> modmapRtrn) {
      this.modmapRtrn = modmapRtrn;
      presentEnable(MapPart.MODIFIER_MAP);
      return this;
    }

    public GetMapReply.GetMapReplyBuilder vmodmapRtrn(List<KeyVModMap> vmodmapRtrn) {
      this.vmodmapRtrn = vmodmapRtrn;
      presentEnable(MapPart.VIRTUAL_MOD_MAP);
      return this;
    }

    public int getSize() {
      return 40 + (MapPart.KEY_TYPES.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(typesRtrn) : 0) + (MapPart.KEY_SYMS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(symsRtrn) : 0) + (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? 1 * actsRtrnCount.size() : 0) + XObject.getSizeForPadAlign(4, (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? 1 * actsRtrnCount.size() : 0)) + (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(actsRtrnActs) : 0) + (MapPart.KEY_BEHAVIORS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(behaviorsRtrn) : 0) + (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present)) ? 1 * vmodsRtrn.size() : 0) + XObject.getSizeForPadAlign(4, (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present)) ? 1 * vmodsRtrn.size() : 0)) + (MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(explicitRtrn) : 0) + XObject.getSizeForPadAlign(4, (MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(explicitRtrn) : 0)) + (MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(modmapRtrn) : 0) + XObject.getSizeForPadAlign(4, (MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(modmapRtrn) : 0)) + (MapPart.VIRTUAL_MOD_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(vmodmapRtrn) : 0);
    }
  }
}
