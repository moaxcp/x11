package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetNamedIndicator implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte OPCODE = 16;

  private short deviceSpec;

  private short ledClass;

  private short ledID;

  private int indicator;

  private boolean setState;

  private boolean on;

  private boolean setMap;

  private boolean createMap;

  private byte mapFlags;

  private byte mapWhichgroups;

  private byte mapGroups;

  private byte mapWhichmods;

  private byte mapRealmods;

  private short mapVmods;

  private int mapCtrls;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetNamedIndicator readSetNamedIndicator(X11Input in) throws IOException {
    SetNamedIndicator.SetNamedIndicatorBuilder javaBuilder = SetNamedIndicator.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short ledClass = in.readCard16();
    short ledID = in.readCard16();
    byte[] pad6 = in.readPad(2);
    int indicator = in.readCard32();
    boolean setState = in.readBool();
    boolean on = in.readBool();
    boolean setMap = in.readBool();
    boolean createMap = in.readBool();
    byte[] pad12 = in.readPad(1);
    byte mapFlags = in.readCard8();
    byte mapWhichgroups = in.readCard8();
    byte mapGroups = in.readCard8();
    byte mapWhichmods = in.readCard8();
    byte mapRealmods = in.readCard8();
    short mapVmods = in.readCard16();
    int mapCtrls = in.readCard32();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.ledClass(ledClass);
    javaBuilder.ledID(ledID);
    javaBuilder.indicator(indicator);
    javaBuilder.setState(setState);
    javaBuilder.on(on);
    javaBuilder.setMap(setMap);
    javaBuilder.createMap(createMap);
    javaBuilder.mapFlags(mapFlags);
    javaBuilder.mapWhichgroups(mapWhichgroups);
    javaBuilder.mapGroups(mapGroups);
    javaBuilder.mapWhichmods(mapWhichmods);
    javaBuilder.mapRealmods(mapRealmods);
    javaBuilder.mapVmods(mapVmods);
    javaBuilder.mapCtrls(mapCtrls);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard16(ledClass);
    out.writeCard16(ledID);
    out.writePad(2);
    out.writeCard32(indicator);
    out.writeBool(setState);
    out.writeBool(on);
    out.writeBool(setMap);
    out.writeBool(createMap);
    out.writePad(1);
    out.writeCard8(mapFlags);
    out.writeCard8(mapWhichgroups);
    out.writeCard8(mapGroups);
    out.writeCard8(mapWhichmods);
    out.writeCard8(mapRealmods);
    out.writeCard16(mapVmods);
    out.writeCard32(mapCtrls);
  }

  public boolean isMapFlagsEnabled(@NonNull IMFlag... maskEnums) {
    for(IMFlag m : maskEnums) {
      if(!m.isEnabled(mapFlags)) {
        return false;
      }
    }
    return true;
  }

  public boolean isMapWhichgroupsEnabled(@NonNull IMGroupsWhich... maskEnums) {
    for(IMGroupsWhich m : maskEnums) {
      if(!m.isEnabled(mapWhichgroups)) {
        return false;
      }
    }
    return true;
  }

  public boolean isMapGroupsEnabled(@NonNull SetOfGroups... maskEnums) {
    for(SetOfGroups m : maskEnums) {
      if(!m.isEnabled(mapGroups)) {
        return false;
      }
    }
    return true;
  }

  public boolean isMapWhichmodsEnabled(@NonNull IMModsWhich... maskEnums) {
    for(IMModsWhich m : maskEnums) {
      if(!m.isEnabled(mapWhichmods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isMapRealmodsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(mapRealmods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isMapVmodsEnabled(@NonNull VMod... maskEnums) {
    for(VMod m : maskEnums) {
      if(!m.isEnabled(mapVmods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isMapCtrlsEnabled(@NonNull BoolCtrl... maskEnums) {
    for(BoolCtrl m : maskEnums) {
      if(!m.isEnabled(mapCtrls)) {
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

  public static class SetNamedIndicatorBuilder {
    public SetNamedIndicator.SetNamedIndicatorBuilder ledClass(LedClass ledClass) {
      this.ledClass = (short) ledClass.getValue();
      return this;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder ledClass(short ledClass) {
      this.ledClass = ledClass;
      return this;
    }

    public boolean isMapFlagsEnabled(@NonNull IMFlag... maskEnums) {
      for(IMFlag m : maskEnums) {
        if(!m.isEnabled(mapFlags)) {
          return false;
        }
      }
      return true;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapFlagsEnable(IMFlag... maskEnums) {
      for(IMFlag m : maskEnums) {
        mapFlags((byte) m.enableFor(mapFlags));
      }
      return this;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapFlagsDisable(IMFlag... maskEnums) {
      for(IMFlag m : maskEnums) {
        mapFlags((byte) m.disableFor(mapFlags));
      }
      return this;
    }

    public boolean isMapWhichgroupsEnabled(@NonNull IMGroupsWhich... maskEnums) {
      for(IMGroupsWhich m : maskEnums) {
        if(!m.isEnabled(mapWhichgroups)) {
          return false;
        }
      }
      return true;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapWhichgroupsEnable(
        IMGroupsWhich... maskEnums) {
      for(IMGroupsWhich m : maskEnums) {
        mapWhichgroups((byte) m.enableFor(mapWhichgroups));
      }
      return this;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapWhichgroupsDisable(
        IMGroupsWhich... maskEnums) {
      for(IMGroupsWhich m : maskEnums) {
        mapWhichgroups((byte) m.disableFor(mapWhichgroups));
      }
      return this;
    }

    public boolean isMapGroupsEnabled(@NonNull SetOfGroups... maskEnums) {
      for(SetOfGroups m : maskEnums) {
        if(!m.isEnabled(mapGroups)) {
          return false;
        }
      }
      return true;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapGroupsEnable(SetOfGroups... maskEnums) {
      for(SetOfGroups m : maskEnums) {
        mapGroups((byte) m.enableFor(mapGroups));
      }
      return this;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapGroupsDisable(SetOfGroups... maskEnums) {
      for(SetOfGroups m : maskEnums) {
        mapGroups((byte) m.disableFor(mapGroups));
      }
      return this;
    }

    public boolean isMapWhichmodsEnabled(@NonNull IMModsWhich... maskEnums) {
      for(IMModsWhich m : maskEnums) {
        if(!m.isEnabled(mapWhichmods)) {
          return false;
        }
      }
      return true;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapWhichmodsEnable(IMModsWhich... maskEnums) {
      for(IMModsWhich m : maskEnums) {
        mapWhichmods((byte) m.enableFor(mapWhichmods));
      }
      return this;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapWhichmodsDisable(
        IMModsWhich... maskEnums) {
      for(IMModsWhich m : maskEnums) {
        mapWhichmods((byte) m.disableFor(mapWhichmods));
      }
      return this;
    }

    public boolean isMapRealmodsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(mapRealmods)) {
          return false;
        }
      }
      return true;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapRealmodsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mapRealmods((byte) m.enableFor(mapRealmods));
      }
      return this;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapRealmodsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mapRealmods((byte) m.disableFor(mapRealmods));
      }
      return this;
    }

    public boolean isMapVmodsEnabled(@NonNull VMod... maskEnums) {
      for(VMod m : maskEnums) {
        if(!m.isEnabled(mapVmods)) {
          return false;
        }
      }
      return true;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapVmodsEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        mapVmods((short) m.enableFor(mapVmods));
      }
      return this;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapVmodsDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        mapVmods((short) m.disableFor(mapVmods));
      }
      return this;
    }

    public boolean isMapCtrlsEnabled(@NonNull BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        if(!m.isEnabled(mapCtrls)) {
          return false;
        }
      }
      return true;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapCtrlsEnable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        mapCtrls((int) m.enableFor(mapCtrls));
      }
      return this;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapCtrlsDisable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        mapCtrls((int) m.disableFor(mapCtrls));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
