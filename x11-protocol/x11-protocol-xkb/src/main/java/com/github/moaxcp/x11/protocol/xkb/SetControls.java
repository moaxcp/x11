package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetControls implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte OPCODE = 7;

  private short deviceSpec;

  private byte affectInternalRealMods;

  private byte internalRealMods;

  private byte affectIgnoreLockRealMods;

  private byte ignoreLockRealMods;

  private short affectInternalVirtualMods;

  private short internalVirtualMods;

  private short affectIgnoreLockVirtualMods;

  private short ignoreLockVirtualMods;

  private byte mouseKeysDfltBtn;

  private byte groupsWrap;

  private short accessXOptions;

  private int affectEnabledControls;

  private int enabledControls;

  private int changeControls;

  private short repeatDelay;

  private short repeatInterval;

  private short slowKeysDelay;

  private short debounceDelay;

  private short mouseKeysDelay;

  private short mouseKeysInterval;

  private short mouseKeysTimeToMax;

  private short mouseKeysMaxSpeed;

  private short mouseKeysCurve;

  private short accessXTimeout;

  private int accessXTimeoutMask;

  private int accessXTimeoutValues;

  private short accessXTimeoutOptionsMask;

  private short accessXTimeoutOptionsValues;

  @NonNull
  private List<Byte> perKeyRepeat;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetControls readSetControls(X11Input in) throws IOException {
    SetControls.SetControlsBuilder javaBuilder = SetControls.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte affectInternalRealMods = in.readCard8();
    byte internalRealMods = in.readCard8();
    byte affectIgnoreLockRealMods = in.readCard8();
    byte ignoreLockRealMods = in.readCard8();
    short affectInternalVirtualMods = in.readCard16();
    short internalVirtualMods = in.readCard16();
    short affectIgnoreLockVirtualMods = in.readCard16();
    short ignoreLockVirtualMods = in.readCard16();
    byte mouseKeysDfltBtn = in.readCard8();
    byte groupsWrap = in.readCard8();
    short accessXOptions = in.readCard16();
    byte[] pad15 = in.readPad(2);
    int affectEnabledControls = in.readCard32();
    int enabledControls = in.readCard32();
    int changeControls = in.readCard32();
    short repeatDelay = in.readCard16();
    short repeatInterval = in.readCard16();
    short slowKeysDelay = in.readCard16();
    short debounceDelay = in.readCard16();
    short mouseKeysDelay = in.readCard16();
    short mouseKeysInterval = in.readCard16();
    short mouseKeysTimeToMax = in.readCard16();
    short mouseKeysMaxSpeed = in.readCard16();
    short mouseKeysCurve = in.readInt16();
    short accessXTimeout = in.readCard16();
    int accessXTimeoutMask = in.readCard32();
    int accessXTimeoutValues = in.readCard32();
    short accessXTimeoutOptionsMask = in.readCard16();
    short accessXTimeoutOptionsValues = in.readCard16();
    List<Byte> perKeyRepeat = in.readCard8(32);
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.affectInternalRealMods(affectInternalRealMods);
    javaBuilder.internalRealMods(internalRealMods);
    javaBuilder.affectIgnoreLockRealMods(affectIgnoreLockRealMods);
    javaBuilder.ignoreLockRealMods(ignoreLockRealMods);
    javaBuilder.affectInternalVirtualMods(affectInternalVirtualMods);
    javaBuilder.internalVirtualMods(internalVirtualMods);
    javaBuilder.affectIgnoreLockVirtualMods(affectIgnoreLockVirtualMods);
    javaBuilder.ignoreLockVirtualMods(ignoreLockVirtualMods);
    javaBuilder.mouseKeysDfltBtn(mouseKeysDfltBtn);
    javaBuilder.groupsWrap(groupsWrap);
    javaBuilder.accessXOptions(accessXOptions);
    javaBuilder.affectEnabledControls(affectEnabledControls);
    javaBuilder.enabledControls(enabledControls);
    javaBuilder.changeControls(changeControls);
    javaBuilder.repeatDelay(repeatDelay);
    javaBuilder.repeatInterval(repeatInterval);
    javaBuilder.slowKeysDelay(slowKeysDelay);
    javaBuilder.debounceDelay(debounceDelay);
    javaBuilder.mouseKeysDelay(mouseKeysDelay);
    javaBuilder.mouseKeysInterval(mouseKeysInterval);
    javaBuilder.mouseKeysTimeToMax(mouseKeysTimeToMax);
    javaBuilder.mouseKeysMaxSpeed(mouseKeysMaxSpeed);
    javaBuilder.mouseKeysCurve(mouseKeysCurve);
    javaBuilder.accessXTimeout(accessXTimeout);
    javaBuilder.accessXTimeoutMask(accessXTimeoutMask);
    javaBuilder.accessXTimeoutValues(accessXTimeoutValues);
    javaBuilder.accessXTimeoutOptionsMask(accessXTimeoutOptionsMask);
    javaBuilder.accessXTimeoutOptionsValues(accessXTimeoutOptionsValues);
    javaBuilder.perKeyRepeat(perKeyRepeat);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard8(affectInternalRealMods);
    out.writeCard8(internalRealMods);
    out.writeCard8(affectIgnoreLockRealMods);
    out.writeCard8(ignoreLockRealMods);
    out.writeCard16(affectInternalVirtualMods);
    out.writeCard16(internalVirtualMods);
    out.writeCard16(affectIgnoreLockVirtualMods);
    out.writeCard16(ignoreLockVirtualMods);
    out.writeCard8(mouseKeysDfltBtn);
    out.writeCard8(groupsWrap);
    out.writeCard16(accessXOptions);
    out.writePad(2);
    out.writeCard32(affectEnabledControls);
    out.writeCard32(enabledControls);
    out.writeCard32(changeControls);
    out.writeCard16(repeatDelay);
    out.writeCard16(repeatInterval);
    out.writeCard16(slowKeysDelay);
    out.writeCard16(debounceDelay);
    out.writeCard16(mouseKeysDelay);
    out.writeCard16(mouseKeysInterval);
    out.writeCard16(mouseKeysTimeToMax);
    out.writeCard16(mouseKeysMaxSpeed);
    out.writeInt16(mouseKeysCurve);
    out.writeCard16(accessXTimeout);
    out.writeCard32(accessXTimeoutMask);
    out.writeCard32(accessXTimeoutValues);
    out.writeCard16(accessXTimeoutOptionsMask);
    out.writeCard16(accessXTimeoutOptionsValues);
    out.writeCard8(perKeyRepeat);
    out.writePadAlign(getSize());
  }

  public boolean isAffectInternalRealModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(affectInternalRealMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isInternalRealModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(internalRealMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectIgnoreLockRealModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(affectIgnoreLockRealMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isIgnoreLockRealModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(ignoreLockRealMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectInternalVirtualModsEnabled(@NonNull VMod... maskEnums) {
    for(VMod m : maskEnums) {
      if(!m.isEnabled(affectInternalVirtualMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isInternalVirtualModsEnabled(@NonNull VMod... maskEnums) {
    for(VMod m : maskEnums) {
      if(!m.isEnabled(internalVirtualMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectIgnoreLockVirtualModsEnabled(@NonNull VMod... maskEnums) {
    for(VMod m : maskEnums) {
      if(!m.isEnabled(affectIgnoreLockVirtualMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isIgnoreLockVirtualModsEnabled(@NonNull VMod... maskEnums) {
    for(VMod m : maskEnums) {
      if(!m.isEnabled(ignoreLockVirtualMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAccessXOptionsEnabled(@NonNull AXOption... maskEnums) {
    for(AXOption m : maskEnums) {
      if(!m.isEnabled(accessXOptions)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectEnabledControlsEnabled(@NonNull BoolCtrl... maskEnums) {
    for(BoolCtrl m : maskEnums) {
      if(!m.isEnabled(affectEnabledControls)) {
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

  public boolean isChangeControlsEnabled(@NonNull Control... maskEnums) {
    for(Control m : maskEnums) {
      if(!m.isEnabled(changeControls)) {
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

  @Override
  public int getSize() {
    return 68 + 1 * perKeyRepeat.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetControlsBuilder {
    public boolean isAffectInternalRealModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(affectInternalRealMods)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder affectInternalRealModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        affectInternalRealMods((byte) m.enableFor(affectInternalRealMods));
      }
      return this;
    }

    public SetControls.SetControlsBuilder affectInternalRealModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        affectInternalRealMods((byte) m.disableFor(affectInternalRealMods));
      }
      return this;
    }

    public boolean isInternalRealModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(internalRealMods)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder internalRealModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        internalRealMods((byte) m.enableFor(internalRealMods));
      }
      return this;
    }

    public SetControls.SetControlsBuilder internalRealModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        internalRealMods((byte) m.disableFor(internalRealMods));
      }
      return this;
    }

    public boolean isAffectIgnoreLockRealModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(affectIgnoreLockRealMods)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder affectIgnoreLockRealModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        affectIgnoreLockRealMods((byte) m.enableFor(affectIgnoreLockRealMods));
      }
      return this;
    }

    public SetControls.SetControlsBuilder affectIgnoreLockRealModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        affectIgnoreLockRealMods((byte) m.disableFor(affectIgnoreLockRealMods));
      }
      return this;
    }

    public boolean isIgnoreLockRealModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(ignoreLockRealMods)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder ignoreLockRealModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        ignoreLockRealMods((byte) m.enableFor(ignoreLockRealMods));
      }
      return this;
    }

    public SetControls.SetControlsBuilder ignoreLockRealModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        ignoreLockRealMods((byte) m.disableFor(ignoreLockRealMods));
      }
      return this;
    }

    public boolean isAffectInternalVirtualModsEnabled(@NonNull VMod... maskEnums) {
      for(VMod m : maskEnums) {
        if(!m.isEnabled(affectInternalVirtualMods)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder affectInternalVirtualModsEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        affectInternalVirtualMods((short) m.enableFor(affectInternalVirtualMods));
      }
      return this;
    }

    public SetControls.SetControlsBuilder affectInternalVirtualModsDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        affectInternalVirtualMods((short) m.disableFor(affectInternalVirtualMods));
      }
      return this;
    }

    public boolean isInternalVirtualModsEnabled(@NonNull VMod... maskEnums) {
      for(VMod m : maskEnums) {
        if(!m.isEnabled(internalVirtualMods)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder internalVirtualModsEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        internalVirtualMods((short) m.enableFor(internalVirtualMods));
      }
      return this;
    }

    public SetControls.SetControlsBuilder internalVirtualModsDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        internalVirtualMods((short) m.disableFor(internalVirtualMods));
      }
      return this;
    }

    public boolean isAffectIgnoreLockVirtualModsEnabled(@NonNull VMod... maskEnums) {
      for(VMod m : maskEnums) {
        if(!m.isEnabled(affectIgnoreLockVirtualMods)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder affectIgnoreLockVirtualModsEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        affectIgnoreLockVirtualMods((short) m.enableFor(affectIgnoreLockVirtualMods));
      }
      return this;
    }

    public SetControls.SetControlsBuilder affectIgnoreLockVirtualModsDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        affectIgnoreLockVirtualMods((short) m.disableFor(affectIgnoreLockVirtualMods));
      }
      return this;
    }

    public boolean isIgnoreLockVirtualModsEnabled(@NonNull VMod... maskEnums) {
      for(VMod m : maskEnums) {
        if(!m.isEnabled(ignoreLockVirtualMods)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder ignoreLockVirtualModsEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        ignoreLockVirtualMods((short) m.enableFor(ignoreLockVirtualMods));
      }
      return this;
    }

    public SetControls.SetControlsBuilder ignoreLockVirtualModsDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        ignoreLockVirtualMods((short) m.disableFor(ignoreLockVirtualMods));
      }
      return this;
    }

    public boolean isAccessXOptionsEnabled(@NonNull AXOption... maskEnums) {
      for(AXOption m : maskEnums) {
        if(!m.isEnabled(accessXOptions)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder accessXOptionsEnable(AXOption... maskEnums) {
      for(AXOption m : maskEnums) {
        accessXOptions((short) m.enableFor(accessXOptions));
      }
      return this;
    }

    public SetControls.SetControlsBuilder accessXOptionsDisable(AXOption... maskEnums) {
      for(AXOption m : maskEnums) {
        accessXOptions((short) m.disableFor(accessXOptions));
      }
      return this;
    }

    public boolean isAffectEnabledControlsEnabled(@NonNull BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        if(!m.isEnabled(affectEnabledControls)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder affectEnabledControlsEnable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        affectEnabledControls((int) m.enableFor(affectEnabledControls));
      }
      return this;
    }

    public SetControls.SetControlsBuilder affectEnabledControlsDisable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        affectEnabledControls((int) m.disableFor(affectEnabledControls));
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

    public SetControls.SetControlsBuilder enabledControlsEnable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        enabledControls((int) m.enableFor(enabledControls));
      }
      return this;
    }

    public SetControls.SetControlsBuilder enabledControlsDisable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        enabledControls((int) m.disableFor(enabledControls));
      }
      return this;
    }

    public boolean isChangeControlsEnabled(@NonNull Control... maskEnums) {
      for(Control m : maskEnums) {
        if(!m.isEnabled(changeControls)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder changeControlsEnable(Control... maskEnums) {
      for(Control m : maskEnums) {
        changeControls((int) m.enableFor(changeControls));
      }
      return this;
    }

    public SetControls.SetControlsBuilder changeControlsDisable(Control... maskEnums) {
      for(Control m : maskEnums) {
        changeControls((int) m.disableFor(changeControls));
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

    public SetControls.SetControlsBuilder accessXTimeoutMaskEnable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        accessXTimeoutMask((int) m.enableFor(accessXTimeoutMask));
      }
      return this;
    }

    public SetControls.SetControlsBuilder accessXTimeoutMaskDisable(BoolCtrl... maskEnums) {
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

    public SetControls.SetControlsBuilder accessXTimeoutValuesEnable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        accessXTimeoutValues((int) m.enableFor(accessXTimeoutValues));
      }
      return this;
    }

    public SetControls.SetControlsBuilder accessXTimeoutValuesDisable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        accessXTimeoutValues((int) m.disableFor(accessXTimeoutValues));
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

    public SetControls.SetControlsBuilder accessXTimeoutOptionsMaskEnable(AXOption... maskEnums) {
      for(AXOption m : maskEnums) {
        accessXTimeoutOptionsMask((short) m.enableFor(accessXTimeoutOptionsMask));
      }
      return this;
    }

    public SetControls.SetControlsBuilder accessXTimeoutOptionsMaskDisable(AXOption... maskEnums) {
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

    public SetControls.SetControlsBuilder accessXTimeoutOptionsValuesEnable(AXOption... maskEnums) {
      for(AXOption m : maskEnums) {
        accessXTimeoutOptionsValues((short) m.enableFor(accessXTimeoutOptionsValues));
      }
      return this;
    }

    public SetControls.SetControlsBuilder accessXTimeoutOptionsValuesDisable(
        AXOption... maskEnums) {
      for(AXOption m : maskEnums) {
        accessXTimeoutOptionsValues((short) m.disableFor(accessXTimeoutOptionsValues));
      }
      return this;
    }

    public int getSize() {
      return 68 + 1 * perKeyRepeat.size();
    }
  }
}
