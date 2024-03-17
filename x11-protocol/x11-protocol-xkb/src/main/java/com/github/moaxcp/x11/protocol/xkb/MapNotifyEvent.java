package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class MapNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte NUMBER = 1;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte xkbType;

  private short sequenceNumber;

  private int time;

  private byte deviceID;

  private byte ptrBtnActions;

  private short changed;

  private byte minKeyCode;

  private byte maxKeyCode;

  private byte firstType;

  private byte nTypes;

  private byte firstKeySym;

  private byte nKeySyms;

  private byte firstKeyAct;

  private byte nKeyActs;

  private byte firstKeyBehavior;

  private byte nKeyBehavior;

  private byte firstKeyExplicit;

  private byte nKeyExplicit;

  private byte firstModMapKey;

  private byte nModMapKeys;

  private byte firstVModMapKey;

  private byte nVModMapKeys;

  private short virtualMods;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static MapNotifyEvent readMapNotifyEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    MapNotifyEvent.MapNotifyEventBuilder javaBuilder = MapNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte ptrBtnActions = in.readCard8();
    short changed = in.readCard16();
    byte minKeyCode = in.readCard8();
    byte maxKeyCode = in.readCard8();
    byte firstType = in.readCard8();
    byte nTypes = in.readCard8();
    byte firstKeySym = in.readCard8();
    byte nKeySyms = in.readCard8();
    byte firstKeyAct = in.readCard8();
    byte nKeyActs = in.readCard8();
    byte firstKeyBehavior = in.readCard8();
    byte nKeyBehavior = in.readCard8();
    byte firstKeyExplicit = in.readCard8();
    byte nKeyExplicit = in.readCard8();
    byte firstModMapKey = in.readCard8();
    byte nModMapKeys = in.readCard8();
    byte firstVModMapKey = in.readCard8();
    byte nVModMapKeys = in.readCard8();
    short virtualMods = in.readCard16();
    byte[] pad24 = in.readPad(2);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.ptrBtnActions(ptrBtnActions);
    javaBuilder.changed(changed);
    javaBuilder.minKeyCode(minKeyCode);
    javaBuilder.maxKeyCode(maxKeyCode);
    javaBuilder.firstType(firstType);
    javaBuilder.nTypes(nTypes);
    javaBuilder.firstKeySym(firstKeySym);
    javaBuilder.nKeySyms(nKeySyms);
    javaBuilder.firstKeyAct(firstKeyAct);
    javaBuilder.nKeyActs(nKeyActs);
    javaBuilder.firstKeyBehavior(firstKeyBehavior);
    javaBuilder.nKeyBehavior(nKeyBehavior);
    javaBuilder.firstKeyExplicit(firstKeyExplicit);
    javaBuilder.nKeyExplicit(nKeyExplicit);
    javaBuilder.firstModMapKey(firstModMapKey);
    javaBuilder.nModMapKeys(nModMapKeys);
    javaBuilder.firstVModMapKey(firstVModMapKey);
    javaBuilder.nVModMapKeys(nVModMapKeys);
    javaBuilder.virtualMods(virtualMods);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writeCard8(xkbType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard8(deviceID);
    out.writeCard8(ptrBtnActions);
    out.writeCard16(changed);
    out.writeCard8(minKeyCode);
    out.writeCard8(maxKeyCode);
    out.writeCard8(firstType);
    out.writeCard8(nTypes);
    out.writeCard8(firstKeySym);
    out.writeCard8(nKeySyms);
    out.writeCard8(firstKeyAct);
    out.writeCard8(nKeyActs);
    out.writeCard8(firstKeyBehavior);
    out.writeCard8(nKeyBehavior);
    out.writeCard8(firstKeyExplicit);
    out.writeCard8(nKeyExplicit);
    out.writeCard8(firstModMapKey);
    out.writeCard8(nModMapKeys);
    out.writeCard8(firstVModMapKey);
    out.writeCard8(nVModMapKeys);
    out.writeCard16(virtualMods);
    out.writePad(2);
  }

  public boolean isChangedEnabled(@NonNull MapPart... maskEnums) {
    for(MapPart m : maskEnums) {
      if(!m.isEnabled(changed)) {
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
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class MapNotifyEventBuilder {
    public boolean isChangedEnabled(@NonNull MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        if(!m.isEnabled(changed)) {
          return false;
        }
      }
      return true;
    }

    public MapNotifyEvent.MapNotifyEventBuilder changedEnable(MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        changed((short) m.enableFor(changed));
      }
      return this;
    }

    public MapNotifyEvent.MapNotifyEventBuilder changedDisable(MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        changed((short) m.disableFor(changed));
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

    public MapNotifyEvent.MapNotifyEventBuilder virtualModsEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        virtualMods((short) m.enableFor(virtualMods));
      }
      return this;
    }

    public MapNotifyEvent.MapNotifyEventBuilder virtualModsDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        virtualMods((short) m.disableFor(virtualMods));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
