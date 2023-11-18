package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import com.github.moaxcp.x11client.protocol.xproto.ModMask;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetNamedIndicatorReply implements XReply, XkbObject {
  private byte deviceID;

  private short sequenceNumber;

  private int indicator;

  private boolean found;

  private boolean on;

  private boolean realIndicator;

  private byte ndx;

  private byte mapFlags;

  private byte mapWhichgroups;

  private byte mapGroups;

  private byte mapWhichmods;

  private byte mapMods;

  private byte mapRealmods;

  private short mapVmod;

  private int mapCtrls;

  private boolean supported;

  public static GetNamedIndicatorReply readGetNamedIndicatorReply(byte deviceID,
      short sequenceNumber, X11Input in) throws IOException {
    GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder javaBuilder = GetNamedIndicatorReply.builder();
    int length = in.readCard32();
    int indicator = in.readCard32();
    boolean found = in.readBool();
    boolean on = in.readBool();
    boolean realIndicator = in.readBool();
    byte ndx = in.readCard8();
    byte mapFlags = in.readCard8();
    byte mapWhichgroups = in.readCard8();
    byte mapGroups = in.readCard8();
    byte mapWhichmods = in.readCard8();
    byte mapMods = in.readCard8();
    byte mapRealmods = in.readCard8();
    short mapVmod = in.readCard16();
    int mapCtrls = in.readCard32();
    boolean supported = in.readBool();
    byte[] pad18 = in.readPad(3);
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.indicator(indicator);
    javaBuilder.found(found);
    javaBuilder.on(on);
    javaBuilder.realIndicator(realIndicator);
    javaBuilder.ndx(ndx);
    javaBuilder.mapFlags(mapFlags);
    javaBuilder.mapWhichgroups(mapWhichgroups);
    javaBuilder.mapGroups(mapGroups);
    javaBuilder.mapWhichmods(mapWhichmods);
    javaBuilder.mapMods(mapMods);
    javaBuilder.mapRealmods(mapRealmods);
    javaBuilder.mapVmod(mapVmod);
    javaBuilder.mapCtrls(mapCtrls);
    javaBuilder.supported(supported);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(deviceID);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(indicator);
    out.writeBool(found);
    out.writeBool(on);
    out.writeBool(realIndicator);
    out.writeCard8(ndx);
    out.writeCard8(mapFlags);
    out.writeCard8(mapWhichgroups);
    out.writeCard8(mapGroups);
    out.writeCard8(mapWhichmods);
    out.writeCard8(mapMods);
    out.writeCard8(mapRealmods);
    out.writeCard16(mapVmod);
    out.writeCard32(mapCtrls);
    out.writeBool(supported);
    out.writePad(3);
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

  public boolean isMapModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(mapMods)) {
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

  public boolean isMapVmodEnabled(@NonNull VMod... maskEnums) {
    for(VMod m : maskEnums) {
      if(!m.isEnabled(mapVmod)) {
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

  public static class GetNamedIndicatorReplyBuilder {
    public boolean isMapFlagsEnabled(@NonNull IMFlag... maskEnums) {
      for(IMFlag m : maskEnums) {
        if(!m.isEnabled(mapFlags)) {
          return false;
        }
      }
      return true;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapFlagsEnable(
        IMFlag... maskEnums) {
      for(IMFlag m : maskEnums) {
        mapFlags((byte) m.enableFor(mapFlags));
      }
      return this;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapFlagsDisable(
        IMFlag... maskEnums) {
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

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapWhichgroupsEnable(
        IMGroupsWhich... maskEnums) {
      for(IMGroupsWhich m : maskEnums) {
        mapWhichgroups((byte) m.enableFor(mapWhichgroups));
      }
      return this;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapWhichgroupsDisable(
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

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapGroupsEnable(
        SetOfGroups... maskEnums) {
      for(SetOfGroups m : maskEnums) {
        mapGroups((byte) m.enableFor(mapGroups));
      }
      return this;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapGroupsDisable(
        SetOfGroups... maskEnums) {
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

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapWhichmodsEnable(
        IMModsWhich... maskEnums) {
      for(IMModsWhich m : maskEnums) {
        mapWhichmods((byte) m.enableFor(mapWhichmods));
      }
      return this;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapWhichmodsDisable(
        IMModsWhich... maskEnums) {
      for(IMModsWhich m : maskEnums) {
        mapWhichmods((byte) m.disableFor(mapWhichmods));
      }
      return this;
    }

    public boolean isMapModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(mapMods)) {
          return false;
        }
      }
      return true;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapModsEnable(
        ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mapMods((byte) m.enableFor(mapMods));
      }
      return this;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapModsDisable(
        ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mapMods((byte) m.disableFor(mapMods));
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

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapRealmodsEnable(
        ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mapRealmods((byte) m.enableFor(mapRealmods));
      }
      return this;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapRealmodsDisable(
        ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mapRealmods((byte) m.disableFor(mapRealmods));
      }
      return this;
    }

    public boolean isMapVmodEnabled(@NonNull VMod... maskEnums) {
      for(VMod m : maskEnums) {
        if(!m.isEnabled(mapVmod)) {
          return false;
        }
      }
      return true;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapVmodEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        mapVmod((short) m.enableFor(mapVmod));
      }
      return this;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapVmodDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        mapVmod((short) m.disableFor(mapVmod));
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

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapCtrlsEnable(
        BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        mapCtrls((int) m.enableFor(mapCtrls));
      }
      return this;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapCtrlsDisable(
        BoolCtrl... maskEnums) {
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
