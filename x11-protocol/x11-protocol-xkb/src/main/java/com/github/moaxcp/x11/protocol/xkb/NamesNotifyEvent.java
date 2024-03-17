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
public class NamesNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte NUMBER = 6;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte xkbType;

  private short sequenceNumber;

  private int time;

  private byte deviceID;

  private short changed;

  private byte firstType;

  private byte nTypes;

  private byte firstLevelName;

  private byte nLevelNames;

  private byte nRadioGroups;

  private byte nKeyAliases;

  private byte changedGroupNames;

  private short changedVirtualMods;

  private byte firstKey;

  private byte nKeys;

  private int changedIndicators;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static NamesNotifyEvent readNamesNotifyEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    NamesNotifyEvent.NamesNotifyEventBuilder javaBuilder = NamesNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte[] pad5 = in.readPad(1);
    short changed = in.readCard16();
    byte firstType = in.readCard8();
    byte nTypes = in.readCard8();
    byte firstLevelName = in.readCard8();
    byte nLevelNames = in.readCard8();
    byte[] pad11 = in.readPad(1);
    byte nRadioGroups = in.readCard8();
    byte nKeyAliases = in.readCard8();
    byte changedGroupNames = in.readCard8();
    short changedVirtualMods = in.readCard16();
    byte firstKey = in.readCard8();
    byte nKeys = in.readCard8();
    int changedIndicators = in.readCard32();
    byte[] pad19 = in.readPad(4);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.changed(changed);
    javaBuilder.firstType(firstType);
    javaBuilder.nTypes(nTypes);
    javaBuilder.firstLevelName(firstLevelName);
    javaBuilder.nLevelNames(nLevelNames);
    javaBuilder.nRadioGroups(nRadioGroups);
    javaBuilder.nKeyAliases(nKeyAliases);
    javaBuilder.changedGroupNames(changedGroupNames);
    javaBuilder.changedVirtualMods(changedVirtualMods);
    javaBuilder.firstKey(firstKey);
    javaBuilder.nKeys(nKeys);
    javaBuilder.changedIndicators(changedIndicators);

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
    out.writePad(1);
    out.writeCard16(changed);
    out.writeCard8(firstType);
    out.writeCard8(nTypes);
    out.writeCard8(firstLevelName);
    out.writeCard8(nLevelNames);
    out.writePad(1);
    out.writeCard8(nRadioGroups);
    out.writeCard8(nKeyAliases);
    out.writeCard8(changedGroupNames);
    out.writeCard16(changedVirtualMods);
    out.writeCard8(firstKey);
    out.writeCard8(nKeys);
    out.writeCard32(changedIndicators);
    out.writePad(4);
  }

  public boolean isChangedEnabled(@NonNull NameDetail... maskEnums) {
    for(NameDetail m : maskEnums) {
      if(!m.isEnabled(changed)) {
        return false;
      }
    }
    return true;
  }

  public boolean isChangedGroupNamesEnabled(@NonNull SetOfGroup... maskEnums) {
    for(SetOfGroup m : maskEnums) {
      if(!m.isEnabled(changedGroupNames)) {
        return false;
      }
    }
    return true;
  }

  public boolean isChangedVirtualModsEnabled(@NonNull VMod... maskEnums) {
    for(VMod m : maskEnums) {
      if(!m.isEnabled(changedVirtualMods)) {
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

  public static class NamesNotifyEventBuilder {
    public boolean isChangedEnabled(@NonNull NameDetail... maskEnums) {
      for(NameDetail m : maskEnums) {
        if(!m.isEnabled(changed)) {
          return false;
        }
      }
      return true;
    }

    public NamesNotifyEvent.NamesNotifyEventBuilder changedEnable(NameDetail... maskEnums) {
      for(NameDetail m : maskEnums) {
        changed((short) m.enableFor(changed));
      }
      return this;
    }

    public NamesNotifyEvent.NamesNotifyEventBuilder changedDisable(NameDetail... maskEnums) {
      for(NameDetail m : maskEnums) {
        changed((short) m.disableFor(changed));
      }
      return this;
    }

    public boolean isChangedGroupNamesEnabled(@NonNull SetOfGroup... maskEnums) {
      for(SetOfGroup m : maskEnums) {
        if(!m.isEnabled(changedGroupNames)) {
          return false;
        }
      }
      return true;
    }

    public NamesNotifyEvent.NamesNotifyEventBuilder changedGroupNamesEnable(
        SetOfGroup... maskEnums) {
      for(SetOfGroup m : maskEnums) {
        changedGroupNames((byte) m.enableFor(changedGroupNames));
      }
      return this;
    }

    public NamesNotifyEvent.NamesNotifyEventBuilder changedGroupNamesDisable(
        SetOfGroup... maskEnums) {
      for(SetOfGroup m : maskEnums) {
        changedGroupNames((byte) m.disableFor(changedGroupNames));
      }
      return this;
    }

    public boolean isChangedVirtualModsEnabled(@NonNull VMod... maskEnums) {
      for(VMod m : maskEnums) {
        if(!m.isEnabled(changedVirtualMods)) {
          return false;
        }
      }
      return true;
    }

    public NamesNotifyEvent.NamesNotifyEventBuilder changedVirtualModsEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        changedVirtualMods((short) m.enableFor(changedVirtualMods));
      }
      return this;
    }

    public NamesNotifyEvent.NamesNotifyEventBuilder changedVirtualModsDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        changedVirtualMods((short) m.disableFor(changedVirtualMods));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
