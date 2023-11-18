package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import com.github.moaxcp.x11client.protocol.xproto.ModMask;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetControlsReply implements XReply, XkbObject {
  private byte deviceID;

  private short sequenceNumber;

  private byte mouseKeysDfltBtn;

  private byte numGroups;

  private byte groupsWrap;

  private byte internalModsMask;

  private byte ignoreLockModsMask;

  private byte internalModsRealMods;

  private byte ignoreLockModsRealMods;

  private short internalModsVmods;

  private short ignoreLockModsVmods;

  private short repeatDelay;

  private short repeatInterval;

  private short slowKeysDelay;

  private short debounceDelay;

  private short mouseKeysDelay;

  private short mouseKeysInterval;

  private short mouseKeysTimeToMax;

  private short mouseKeysMaxSpeed;

  private short mouseKeysCurve;

  private short accessXOption;

  private short accessXTimeout;

  private short accessXTimeoutOptionsMask;

  private short accessXTimeoutOptionsValues;

  private int accessXTimeoutMask;

  private int accessXTimeoutValues;

  private int enabledControls;

  @NonNull
  private List<Byte> perKeyRepeat;

  public static GetControlsReply readGetControlsReply(byte deviceID, short sequenceNumber,
      X11Input in) throws IOException {
    GetControlsReply.GetControlsReplyBuilder javaBuilder = GetControlsReply.builder();
    int length = in.readCard32();
    byte mouseKeysDfltBtn = in.readCard8();
    byte numGroups = in.readCard8();
    byte groupsWrap = in.readCard8();
    byte internalModsMask = in.readCard8();
    byte ignoreLockModsMask = in.readCard8();
    byte internalModsRealMods = in.readCard8();
    byte ignoreLockModsRealMods = in.readCard8();
    byte[] pad11 = in.readPad(1);
    short internalModsVmods = in.readCard16();
    short ignoreLockModsVmods = in.readCard16();
    short repeatDelay = in.readCard16();
    short repeatInterval = in.readCard16();
    short slowKeysDelay = in.readCard16();
    short debounceDelay = in.readCard16();
    short mouseKeysDelay = in.readCard16();
    short mouseKeysInterval = in.readCard16();
    short mouseKeysTimeToMax = in.readCard16();
    short mouseKeysMaxSpeed = in.readCard16();
    short mouseKeysCurve = in.readInt16();
    short accessXOption = in.readCard16();
    short accessXTimeout = in.readCard16();
    short accessXTimeoutOptionsMask = in.readCard16();
    short accessXTimeoutOptionsValues = in.readCard16();
    byte[] pad27 = in.readPad(2);
    int accessXTimeoutMask = in.readCard32();
    int accessXTimeoutValues = in.readCard32();
    int enabledControls = in.readCard32();
    List<Byte> perKeyRepeat = in.readCard8(32);
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.mouseKeysDfltBtn(mouseKeysDfltBtn);
    javaBuilder.numGroups(numGroups);
    javaBuilder.groupsWrap(groupsWrap);
    javaBuilder.internalModsMask(internalModsMask);
    javaBuilder.ignoreLockModsMask(ignoreLockModsMask);
    javaBuilder.internalModsRealMods(internalModsRealMods);
    javaBuilder.ignoreLockModsRealMods(ignoreLockModsRealMods);
    javaBuilder.internalModsVmods(internalModsVmods);
    javaBuilder.ignoreLockModsVmods(ignoreLockModsVmods);
    javaBuilder.repeatDelay(repeatDelay);
    javaBuilder.repeatInterval(repeatInterval);
    javaBuilder.slowKeysDelay(slowKeysDelay);
    javaBuilder.debounceDelay(debounceDelay);
    javaBuilder.mouseKeysDelay(mouseKeysDelay);
    javaBuilder.mouseKeysInterval(mouseKeysInterval);
    javaBuilder.mouseKeysTimeToMax(mouseKeysTimeToMax);
    javaBuilder.mouseKeysMaxSpeed(mouseKeysMaxSpeed);
    javaBuilder.mouseKeysCurve(mouseKeysCurve);
    javaBuilder.accessXOption(accessXOption);
    javaBuilder.accessXTimeout(accessXTimeout);
    javaBuilder.accessXTimeoutOptionsMask(accessXTimeoutOptionsMask);
    javaBuilder.accessXTimeoutOptionsValues(accessXTimeoutOptionsValues);
    javaBuilder.accessXTimeoutMask(accessXTimeoutMask);
    javaBuilder.accessXTimeoutValues(accessXTimeoutValues);
    javaBuilder.enabledControls(enabledControls);
    javaBuilder.perKeyRepeat(perKeyRepeat);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(deviceID);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard8(mouseKeysDfltBtn);
    out.writeCard8(numGroups);
    out.writeCard8(groupsWrap);
    out.writeCard8(internalModsMask);
    out.writeCard8(ignoreLockModsMask);
    out.writeCard8(internalModsRealMods);
    out.writeCard8(ignoreLockModsRealMods);
    out.writePad(1);
    out.writeCard16(internalModsVmods);
    out.writeCard16(ignoreLockModsVmods);
    out.writeCard16(repeatDelay);
    out.writeCard16(repeatInterval);
    out.writeCard16(slowKeysDelay);
    out.writeCard16(debounceDelay);
    out.writeCard16(mouseKeysDelay);
    out.writeCard16(mouseKeysInterval);
    out.writeCard16(mouseKeysTimeToMax);
    out.writeCard16(mouseKeysMaxSpeed);
    out.writeInt16(mouseKeysCurve);
    out.writeCard16(accessXOption);
    out.writeCard16(accessXTimeout);
    out.writeCard16(accessXTimeoutOptionsMask);
    out.writeCard16(accessXTimeoutOptionsValues);
    out.writePad(2);
    out.writeCard32(accessXTimeoutMask);
    out.writeCard32(accessXTimeoutValues);
    out.writeCard32(enabledControls);
    out.writeCard8(perKeyRepeat);
    out.writePadAlign(getSize());
  }

  public boolean isInternalModsMaskEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(internalModsMask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isIgnoreLockModsMaskEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(ignoreLockModsMask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isInternalModsRealModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(internalModsRealMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isIgnoreLockModsRealModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(ignoreLockModsRealMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isInternalModsVmodsEnabled(@NonNull VMod... maskEnums) {
    for(VMod m : maskEnums) {
      if(!m.isEnabled(internalModsVmods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isIgnoreLockModsVmodsEnabled(@NonNull VMod... maskEnums) {
    for(VMod m : maskEnums) {
      if(!m.isEnabled(ignoreLockModsVmods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAccessXOptionEnabled(@NonNull AXOption... maskEnums) {
    for(AXOption m : maskEnums) {
      if(!m.isEnabled(accessXOption)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAccessXTimeoutOptionsMaskEnabled(@NonNull AXOption... maskEnums) {
    for(AXOption m : maskEnums) {
      if(!m.isEnabled(accessXTimeoutOptionsMask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAccessXTimeoutOptionsValuesEnabled(@NonNull AXOption... maskEnums) {
    for(AXOption m : maskEnums) {
      if(!m.isEnabled(accessXTimeoutOptionsValues)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAccessXTimeoutMaskEnabled(@NonNull BoolCtrl... maskEnums) {
    for(BoolCtrl m : maskEnums) {
      if(!m.isEnabled(accessXTimeoutMask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAccessXTimeoutValuesEnabled(@NonNull BoolCtrl... maskEnums) {
    for(BoolCtrl m : maskEnums) {
      if(!m.isEnabled(accessXTimeoutValues)) {
        return false;
      }
    }
    return true;
  }

  public boolean isEnabledControlsEnabled(@NonNull BoolCtrl... maskEnums) {
    for(BoolCtrl m : maskEnums) {
      if(!m.isEnabled(enabledControls)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 60 + 1 * perKeyRepeat.size();
  }

  public static class GetControlsReplyBuilder {
    public boolean isInternalModsMaskEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(internalModsMask)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder internalModsMaskEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        internalModsMask((byte) m.enableFor(internalModsMask));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder internalModsMaskDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        internalModsMask((byte) m.disableFor(internalModsMask));
      }
      return this;
    }

    public boolean isIgnoreLockModsMaskEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(ignoreLockModsMask)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder ignoreLockModsMaskEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        ignoreLockModsMask((byte) m.enableFor(ignoreLockModsMask));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder ignoreLockModsMaskDisable(
        ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        ignoreLockModsMask((byte) m.disableFor(ignoreLockModsMask));
      }
      return this;
    }

    public boolean isInternalModsRealModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(internalModsRealMods)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder internalModsRealModsEnable(
        ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        internalModsRealMods((byte) m.enableFor(internalModsRealMods));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder internalModsRealModsDisable(
        ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        internalModsRealMods((byte) m.disableFor(internalModsRealMods));
      }
      return this;
    }

    public boolean isIgnoreLockModsRealModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(ignoreLockModsRealMods)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder ignoreLockModsRealModsEnable(
        ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        ignoreLockModsRealMods((byte) m.enableFor(ignoreLockModsRealMods));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder ignoreLockModsRealModsDisable(
        ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        ignoreLockModsRealMods((byte) m.disableFor(ignoreLockModsRealMods));
      }
      return this;
    }

    public boolean isInternalModsVmodsEnabled(@NonNull VMod... maskEnums) {
      for(VMod m : maskEnums) {
        if(!m.isEnabled(internalModsVmods)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder internalModsVmodsEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        internalModsVmods((short) m.enableFor(internalModsVmods));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder internalModsVmodsDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        internalModsVmods((short) m.disableFor(internalModsVmods));
      }
      return this;
    }

    public boolean isIgnoreLockModsVmodsEnabled(@NonNull VMod... maskEnums) {
      for(VMod m : maskEnums) {
        if(!m.isEnabled(ignoreLockModsVmods)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder ignoreLockModsVmodsEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        ignoreLockModsVmods((short) m.enableFor(ignoreLockModsVmods));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder ignoreLockModsVmodsDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        ignoreLockModsVmods((short) m.disableFor(ignoreLockModsVmods));
      }
      return this;
    }

    public boolean isAccessXOptionEnabled(@NonNull AXOption... maskEnums) {
      for(AXOption m : maskEnums) {
        if(!m.isEnabled(accessXOption)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder accessXOptionEnable(AXOption... maskEnums) {
      for(AXOption m : maskEnums) {
        accessXOption((short) m.enableFor(accessXOption));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder accessXOptionDisable(AXOption... maskEnums) {
      for(AXOption m : maskEnums) {
        accessXOption((short) m.disableFor(accessXOption));
      }
      return this;
    }

    public boolean isAccessXTimeoutOptionsMaskEnabled(@NonNull AXOption... maskEnums) {
      for(AXOption m : maskEnums) {
        if(!m.isEnabled(accessXTimeoutOptionsMask)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutOptionsMaskEnable(
        AXOption... maskEnums) {
      for(AXOption m : maskEnums) {
        accessXTimeoutOptionsMask((short) m.enableFor(accessXTimeoutOptionsMask));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutOptionsMaskDisable(
        AXOption... maskEnums) {
      for(AXOption m : maskEnums) {
        accessXTimeoutOptionsMask((short) m.disableFor(accessXTimeoutOptionsMask));
      }
      return this;
    }

    public boolean isAccessXTimeoutOptionsValuesEnabled(@NonNull AXOption... maskEnums) {
      for(AXOption m : maskEnums) {
        if(!m.isEnabled(accessXTimeoutOptionsValues)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutOptionsValuesEnable(
        AXOption... maskEnums) {
      for(AXOption m : maskEnums) {
        accessXTimeoutOptionsValues((short) m.enableFor(accessXTimeoutOptionsValues));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutOptionsValuesDisable(
        AXOption... maskEnums) {
      for(AXOption m : maskEnums) {
        accessXTimeoutOptionsValues((short) m.disableFor(accessXTimeoutOptionsValues));
      }
      return this;
    }

    public boolean isAccessXTimeoutMaskEnabled(@NonNull BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        if(!m.isEnabled(accessXTimeoutMask)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutMaskEnable(
        BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        accessXTimeoutMask((int) m.enableFor(accessXTimeoutMask));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutMaskDisable(
        BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        accessXTimeoutMask((int) m.disableFor(accessXTimeoutMask));
      }
      return this;
    }

    public boolean isAccessXTimeoutValuesEnabled(@NonNull BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        if(!m.isEnabled(accessXTimeoutValues)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutValuesEnable(
        BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        accessXTimeoutValues((int) m.enableFor(accessXTimeoutValues));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutValuesDisable(
        BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        accessXTimeoutValues((int) m.disableFor(accessXTimeoutValues));
      }
      return this;
    }

    public boolean isEnabledControlsEnabled(@NonNull BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        if(!m.isEnabled(enabledControls)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder enabledControlsEnable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        enabledControls((int) m.enableFor(enabledControls));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder enabledControlsDisable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        enabledControls((int) m.disableFor(enabledControls));
      }
      return this;
    }

    public int getSize() {
      return 60 + 1 * perKeyRepeat.size();
    }
  }
}
