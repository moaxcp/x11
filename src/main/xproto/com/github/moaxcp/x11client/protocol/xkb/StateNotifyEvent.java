package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.xproto.KeyButMask;
import com.github.moaxcp.x11client.protocol.xproto.ModMask;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class StateNotifyEvent implements XEvent, XkbObject {
  public static final byte NUMBER = 2;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte xkbType;

  private short sequenceNumber;

  private int time;

  private byte deviceID;

  private byte mods;

  private byte baseMods;

  private byte latchedMods;

  private byte lockedMods;

  private byte group;

  private short baseGroup;

  private short latchedGroup;

  private byte lockedGroup;

  private byte compatState;

  private byte grabMods;

  private byte compatGrabMods;

  private byte lookupMods;

  private byte compatLoockupMods;

  private short ptrBtnState;

  private short changed;

  private byte keycode;

  private byte eventType;

  private byte requestMajor;

  private byte requestMinor;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static StateNotifyEvent readStateNotifyEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    StateNotifyEvent.StateNotifyEventBuilder javaBuilder = StateNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte mods = in.readCard8();
    byte baseMods = in.readCard8();
    byte latchedMods = in.readCard8();
    byte lockedMods = in.readCard8();
    byte group = in.readCard8();
    short baseGroup = in.readInt16();
    short latchedGroup = in.readInt16();
    byte lockedGroup = in.readCard8();
    byte compatState = in.readCard8();
    byte grabMods = in.readCard8();
    byte compatGrabMods = in.readCard8();
    byte lookupMods = in.readCard8();
    byte compatLoockupMods = in.readCard8();
    short ptrBtnState = in.readCard16();
    short changed = in.readCard16();
    byte keycode = in.readCard8();
    byte eventType = in.readCard8();
    byte requestMajor = in.readCard8();
    byte requestMinor = in.readCard8();
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.mods(mods);
    javaBuilder.baseMods(baseMods);
    javaBuilder.latchedMods(latchedMods);
    javaBuilder.lockedMods(lockedMods);
    javaBuilder.group(group);
    javaBuilder.baseGroup(baseGroup);
    javaBuilder.latchedGroup(latchedGroup);
    javaBuilder.lockedGroup(lockedGroup);
    javaBuilder.compatState(compatState);
    javaBuilder.grabMods(grabMods);
    javaBuilder.compatGrabMods(compatGrabMods);
    javaBuilder.lookupMods(lookupMods);
    javaBuilder.compatLoockupMods(compatLoockupMods);
    javaBuilder.ptrBtnState(ptrBtnState);
    javaBuilder.changed(changed);
    javaBuilder.keycode(keycode);
    javaBuilder.eventType(eventType);
    javaBuilder.requestMajor(requestMajor);
    javaBuilder.requestMinor(requestMinor);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(xkbType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard8(deviceID);
    out.writeCard8(mods);
    out.writeCard8(baseMods);
    out.writeCard8(latchedMods);
    out.writeCard8(lockedMods);
    out.writeCard8(group);
    out.writeInt16(baseGroup);
    out.writeInt16(latchedGroup);
    out.writeCard8(lockedGroup);
    out.writeCard8(compatState);
    out.writeCard8(grabMods);
    out.writeCard8(compatGrabMods);
    out.writeCard8(lookupMods);
    out.writeCard8(compatLoockupMods);
    out.writeCard16(ptrBtnState);
    out.writeCard16(changed);
    out.writeCard8(keycode);
    out.writeCard8(eventType);
    out.writeCard8(requestMajor);
    out.writeCard8(requestMinor);
  }

  public boolean isModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(mods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isBaseModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(baseMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isLatchedModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(latchedMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isLockedModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(lockedMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isCompatStateEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(compatState)) {
        return false;
      }
    }
    return true;
  }

  public boolean isGrabModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(grabMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isCompatGrabModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(compatGrabMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isLookupModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(lookupMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isCompatLoockupModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(compatLoockupMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isPtrBtnStateEnabled(@NonNull KeyButMask... maskEnums) {
    for(KeyButMask m : maskEnums) {
      if(!m.isEnabled(ptrBtnState)) {
        return false;
      }
    }
    return true;
  }

  public boolean isChangedEnabled(@NonNull StatePart... maskEnums) {
    for(StatePart m : maskEnums) {
      if(!m.isEnabled(changed)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class StateNotifyEventBuilder {
    public boolean isModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(mods)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder modsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mods((byte) m.enableFor(mods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder modsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mods((byte) m.disableFor(mods));
      }
      return this;
    }

    public boolean isBaseModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(baseMods)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder baseModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        baseMods((byte) m.enableFor(baseMods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder baseModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        baseMods((byte) m.disableFor(baseMods));
      }
      return this;
    }

    public boolean isLatchedModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(latchedMods)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder latchedModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        latchedMods((byte) m.enableFor(latchedMods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder latchedModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        latchedMods((byte) m.disableFor(latchedMods));
      }
      return this;
    }

    public boolean isLockedModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(lockedMods)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder lockedModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        lockedMods((byte) m.enableFor(lockedMods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder lockedModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        lockedMods((byte) m.disableFor(lockedMods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder group(Group group) {
      this.group = (byte) group.getValue();
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder group(byte group) {
      this.group = group;
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder lockedGroup(Group lockedGroup) {
      this.lockedGroup = (byte) lockedGroup.getValue();
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder lockedGroup(byte lockedGroup) {
      this.lockedGroup = lockedGroup;
      return this;
    }

    public boolean isCompatStateEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(compatState)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder compatStateEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        compatState((byte) m.enableFor(compatState));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder compatStateDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        compatState((byte) m.disableFor(compatState));
      }
      return this;
    }

    public boolean isGrabModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(grabMods)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder grabModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        grabMods((byte) m.enableFor(grabMods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder grabModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        grabMods((byte) m.disableFor(grabMods));
      }
      return this;
    }

    public boolean isCompatGrabModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(compatGrabMods)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder compatGrabModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        compatGrabMods((byte) m.enableFor(compatGrabMods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder compatGrabModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        compatGrabMods((byte) m.disableFor(compatGrabMods));
      }
      return this;
    }

    public boolean isLookupModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(lookupMods)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder lookupModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        lookupMods((byte) m.enableFor(lookupMods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder lookupModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        lookupMods((byte) m.disableFor(lookupMods));
      }
      return this;
    }

    public boolean isCompatLoockupModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(compatLoockupMods)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder compatLoockupModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        compatLoockupMods((byte) m.enableFor(compatLoockupMods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder compatLoockupModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        compatLoockupMods((byte) m.disableFor(compatLoockupMods));
      }
      return this;
    }

    public boolean isPtrBtnStateEnabled(@NonNull KeyButMask... maskEnums) {
      for(KeyButMask m : maskEnums) {
        if(!m.isEnabled(ptrBtnState)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder ptrBtnStateEnable(KeyButMask... maskEnums) {
      for(KeyButMask m : maskEnums) {
        ptrBtnState((short) m.enableFor(ptrBtnState));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder ptrBtnStateDisable(KeyButMask... maskEnums) {
      for(KeyButMask m : maskEnums) {
        ptrBtnState((short) m.disableFor(ptrBtnState));
      }
      return this;
    }

    public boolean isChangedEnabled(@NonNull StatePart... maskEnums) {
      for(StatePart m : maskEnums) {
        if(!m.isEnabled(changed)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder changedEnable(StatePart... maskEnums) {
      for(StatePart m : maskEnums) {
        changed((short) m.enableFor(changed));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder changedDisable(StatePart... maskEnums) {
      for(StatePart m : maskEnums) {
        changed((short) m.disableFor(changed));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
