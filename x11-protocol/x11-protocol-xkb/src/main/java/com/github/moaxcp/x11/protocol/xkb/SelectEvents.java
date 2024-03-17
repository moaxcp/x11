package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SelectEvents implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte OPCODE = 1;

  private short deviceSpec;

  private short affectWhich;

  private short clear;

  private short selectAll;

  private short affectMap;

  private short map;

  private short affectNewKeyboard;

  private short newKeyboardDetails;

  private short affectState;

  private short stateDetails;

  private int affectCtrls;

  private int ctrlDetails;

  private int affectIndicatorState;

  private int indicatorStateDetails;

  private int affectIndicatorMap;

  private int indicatorMapDetails;

  private short affectNames;

  private short namesDetails;

  private byte affectCompat;

  private byte compatDetails;

  private byte affectBell;

  private byte bellDetails;

  private byte affectMsgDetails;

  private byte msgDetails;

  private short affectAccessX;

  private short accessXDetails;

  private short affectExtDev;

  private short extdevDetails;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectEvents readSelectEvents(X11Input in) throws IOException {
    SelectEvents.SelectEventsBuilder javaBuilder = SelectEvents.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short affectWhich = in.readCard16();
    short clear = in.readCard16();
    short selectAll = in.readCard16();
    short affectMap = in.readCard16();
    short map = in.readCard16();
    short affectNewKeyboard = 0;
    short newKeyboardDetails = 0;
    short affectState = 0;
    short stateDetails = 0;
    int affectCtrls = 0;
    int ctrlDetails = 0;
    int affectIndicatorState = 0;
    int indicatorStateDetails = 0;
    int affectIndicatorMap = 0;
    int indicatorMapDetails = 0;
    short affectNames = 0;
    short namesDetails = 0;
    byte affectCompat = 0;
    byte compatDetails = 0;
    byte affectBell = 0;
    byte bellDetails = 0;
    byte affectMsgDetails = 0;
    byte msgDetails = 0;
    short affectAccessX = 0;
    short accessXDetails = 0;
    short affectExtDev = 0;
    short extdevDetails = 0;
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.affectWhich(affectWhich);
    javaBuilder.clear(clear);
    javaBuilder.selectAll(selectAll);
    javaBuilder.affectMap(affectMap);
    javaBuilder.map(map);
    if(EventType.NEW_KEYBOARD_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      affectNewKeyboard = in.readCard16();
      javaBuilder.affectNewKeyboard(affectNewKeyboard);
    }
    if(EventType.NEW_KEYBOARD_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      newKeyboardDetails = in.readCard16();
      javaBuilder.newKeyboardDetails(newKeyboardDetails);
    }
    if(EventType.STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      affectState = in.readCard16();
      javaBuilder.affectState(affectState);
    }
    if(EventType.STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      stateDetails = in.readCard16();
      javaBuilder.stateDetails(stateDetails);
    }
    if(EventType.CONTROLS_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      affectCtrls = in.readCard32();
      javaBuilder.affectCtrls(affectCtrls);
    }
    if(EventType.CONTROLS_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      ctrlDetails = in.readCard32();
      javaBuilder.ctrlDetails(ctrlDetails);
    }
    if(EventType.INDICATOR_STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      affectIndicatorState = in.readCard32();
      javaBuilder.affectIndicatorState(affectIndicatorState);
    }
    if(EventType.INDICATOR_STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      indicatorStateDetails = in.readCard32();
      javaBuilder.indicatorStateDetails(indicatorStateDetails);
    }
    if(EventType.INDICATOR_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      affectIndicatorMap = in.readCard32();
      javaBuilder.affectIndicatorMap(affectIndicatorMap);
    }
    if(EventType.INDICATOR_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      indicatorMapDetails = in.readCard32();
      javaBuilder.indicatorMapDetails(indicatorMapDetails);
    }
    if(EventType.NAMES_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      affectNames = in.readCard16();
      javaBuilder.affectNames(affectNames);
    }
    if(EventType.NAMES_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      namesDetails = in.readCard16();
      javaBuilder.namesDetails(namesDetails);
    }
    if(EventType.COMPAT_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      affectCompat = in.readCard8();
      javaBuilder.affectCompat(affectCompat);
    }
    if(EventType.COMPAT_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      compatDetails = in.readCard8();
      javaBuilder.compatDetails(compatDetails);
    }
    if(EventType.BELL_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      affectBell = in.readCard8();
      javaBuilder.affectBell(affectBell);
    }
    if(EventType.BELL_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      bellDetails = in.readCard8();
      javaBuilder.bellDetails(bellDetails);
    }
    if(EventType.ACTION_MESSAGE.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      affectMsgDetails = in.readCard8();
      javaBuilder.affectMsgDetails(affectMsgDetails);
    }
    if(EventType.ACTION_MESSAGE.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      msgDetails = in.readCard8();
      javaBuilder.msgDetails(msgDetails);
    }
    if(EventType.ACCESS_X_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      affectAccessX = in.readCard16();
      javaBuilder.affectAccessX(affectAccessX);
    }
    if(EventType.ACCESS_X_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      accessXDetails = in.readCard16();
      javaBuilder.accessXDetails(accessXDetails);
    }
    if(EventType.EXTENSION_DEVICE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      affectExtDev = in.readCard16();
      javaBuilder.affectExtDev(affectExtDev);
    }
    if(EventType.EXTENSION_DEVICE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      extdevDetails = in.readCard16();
      javaBuilder.extdevDetails(extdevDetails);
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
    out.writeCard16(affectWhich);
    out.writeCard16(clear);
    out.writeCard16(selectAll);
    out.writeCard16(affectMap);
    out.writeCard16(map);
    if(EventType.NEW_KEYBOARD_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(affectNewKeyboard);
    }
    if(EventType.NEW_KEYBOARD_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(newKeyboardDetails);
    }
    if(EventType.STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(affectState);
    }
    if(EventType.STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(stateDetails);
    }
    if(EventType.CONTROLS_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard32(affectCtrls);
    }
    if(EventType.CONTROLS_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard32(ctrlDetails);
    }
    if(EventType.INDICATOR_STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard32(affectIndicatorState);
    }
    if(EventType.INDICATOR_STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard32(indicatorStateDetails);
    }
    if(EventType.INDICATOR_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard32(affectIndicatorMap);
    }
    if(EventType.INDICATOR_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard32(indicatorMapDetails);
    }
    if(EventType.NAMES_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(affectNames);
    }
    if(EventType.NAMES_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(namesDetails);
    }
    if(EventType.COMPAT_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard8(affectCompat);
    }
    if(EventType.COMPAT_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard8(compatDetails);
    }
    if(EventType.BELL_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard8(affectBell);
    }
    if(EventType.BELL_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard8(bellDetails);
    }
    if(EventType.ACTION_MESSAGE.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard8(affectMsgDetails);
    }
    if(EventType.ACTION_MESSAGE.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard8(msgDetails);
    }
    if(EventType.ACCESS_X_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(affectAccessX);
    }
    if(EventType.ACCESS_X_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(accessXDetails);
    }
    if(EventType.EXTENSION_DEVICE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(affectExtDev);
    }
    if(EventType.EXTENSION_DEVICE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(extdevDetails);
    }
    out.writePadAlign(getSize());
  }

  public boolean isAffectWhichEnabled(@NonNull EventType... maskEnums) {
    for(EventType m : maskEnums) {
      if(!m.isEnabled(affectWhich)) {
        return false;
      }
    }
    return true;
  }

  public boolean isClearEnabled(@NonNull EventType... maskEnums) {
    for(EventType m : maskEnums) {
      if(!m.isEnabled(clear)) {
        return false;
      }
    }
    return true;
  }

  public boolean isSelectAllEnabled(@NonNull EventType... maskEnums) {
    for(EventType m : maskEnums) {
      if(!m.isEnabled(selectAll)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectMapEnabled(@NonNull MapPart... maskEnums) {
    for(MapPart m : maskEnums) {
      if(!m.isEnabled(affectMap)) {
        return false;
      }
    }
    return true;
  }

  public boolean isMapEnabled(@NonNull MapPart... maskEnums) {
    for(MapPart m : maskEnums) {
      if(!m.isEnabled(map)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectNewKeyboardEnabled(@NonNull NKNDetail... maskEnums) {
    for(NKNDetail m : maskEnums) {
      if(!m.isEnabled(affectNewKeyboard)) {
        return false;
      }
    }
    return true;
  }

  public boolean isNewKeyboardDetailsEnabled(@NonNull NKNDetail... maskEnums) {
    for(NKNDetail m : maskEnums) {
      if(!m.isEnabled(newKeyboardDetails)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectStateEnabled(@NonNull StatePart... maskEnums) {
    for(StatePart m : maskEnums) {
      if(!m.isEnabled(affectState)) {
        return false;
      }
    }
    return true;
  }

  public boolean isStateDetailsEnabled(@NonNull StatePart... maskEnums) {
    for(StatePart m : maskEnums) {
      if(!m.isEnabled(stateDetails)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectCtrlsEnabled(@NonNull Control... maskEnums) {
    for(Control m : maskEnums) {
      if(!m.isEnabled(affectCtrls)) {
        return false;
      }
    }
    return true;
  }

  public boolean isCtrlDetailsEnabled(@NonNull Control... maskEnums) {
    for(Control m : maskEnums) {
      if(!m.isEnabled(ctrlDetails)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectNamesEnabled(@NonNull NameDetail... maskEnums) {
    for(NameDetail m : maskEnums) {
      if(!m.isEnabled(affectNames)) {
        return false;
      }
    }
    return true;
  }

  public boolean isNamesDetailsEnabled(@NonNull NameDetail... maskEnums) {
    for(NameDetail m : maskEnums) {
      if(!m.isEnabled(namesDetails)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectCompatEnabled(@NonNull CMDetail... maskEnums) {
    for(CMDetail m : maskEnums) {
      if(!m.isEnabled(affectCompat)) {
        return false;
      }
    }
    return true;
  }

  public boolean isCompatDetailsEnabled(@NonNull CMDetail... maskEnums) {
    for(CMDetail m : maskEnums) {
      if(!m.isEnabled(compatDetails)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectAccessXEnabled(@NonNull AXNDetail... maskEnums) {
    for(AXNDetail m : maskEnums) {
      if(!m.isEnabled(affectAccessX)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAccessXDetailsEnabled(@NonNull AXNDetail... maskEnums) {
    for(AXNDetail m : maskEnums) {
      if(!m.isEnabled(accessXDetails)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectExtDevEnabled(@NonNull XIFeature... maskEnums) {
    for(XIFeature m : maskEnums) {
      if(!m.isEnabled(affectExtDev)) {
        return false;
      }
    }
    return true;
  }

  public boolean isExtdevDetailsEnabled(@NonNull XIFeature... maskEnums) {
    for(XIFeature m : maskEnums) {
      if(!m.isEnabled(extdevDetails)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 16 + (EventType.NEW_KEYBOARD_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.NEW_KEYBOARD_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.CONTROLS_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.CONTROLS_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.INDICATOR_STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.INDICATOR_STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.INDICATOR_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.INDICATOR_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.NAMES_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.NAMES_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.COMPAT_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.COMPAT_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.BELL_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.BELL_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.ACTION_MESSAGE.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.ACTION_MESSAGE.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.ACCESS_X_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.ACCESS_X_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.EXTENSION_DEVICE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.EXTENSION_DEVICE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SelectEventsBuilder {
    public boolean isAffectWhichEnabled(@NonNull EventType... maskEnums) {
      for(EventType m : maskEnums) {
        if(!m.isEnabled(affectWhich)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectWhichEnable(EventType... maskEnums) {
      for(EventType m : maskEnums) {
        affectWhich((short) m.enableFor(affectWhich));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectWhichDisable(EventType... maskEnums) {
      for(EventType m : maskEnums) {
        affectWhich((short) m.disableFor(affectWhich));
      }
      return this;
    }

    public boolean isClearEnabled(@NonNull EventType... maskEnums) {
      for(EventType m : maskEnums) {
        if(!m.isEnabled(clear)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder clearEnable(EventType... maskEnums) {
      for(EventType m : maskEnums) {
        clear((short) m.enableFor(clear));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder clearDisable(EventType... maskEnums) {
      for(EventType m : maskEnums) {
        clear((short) m.disableFor(clear));
      }
      return this;
    }

    public boolean isSelectAllEnabled(@NonNull EventType... maskEnums) {
      for(EventType m : maskEnums) {
        if(!m.isEnabled(selectAll)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder selectAllEnable(EventType... maskEnums) {
      for(EventType m : maskEnums) {
        selectAll((short) m.enableFor(selectAll));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder selectAllDisable(EventType... maskEnums) {
      for(EventType m : maskEnums) {
        selectAll((short) m.disableFor(selectAll));
      }
      return this;
    }

    public boolean isAffectMapEnabled(@NonNull MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        if(!m.isEnabled(affectMap)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectMapEnable(MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        affectMap((short) m.enableFor(affectMap));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectMapDisable(MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        affectMap((short) m.disableFor(affectMap));
      }
      return this;
    }

    public boolean isMapEnabled(@NonNull MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        if(!m.isEnabled(map)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder mapEnable(MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        map((short) m.enableFor(map));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder mapDisable(MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        map((short) m.disableFor(map));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectNewKeyboard(short affectNewKeyboard) {
      this.affectNewKeyboard = affectNewKeyboard;
      affectWhichEnable(EventType.NEW_KEYBOARD_NOTIFY);
      return this;
    }

    public boolean isAffectNewKeyboardEnabled(@NonNull NKNDetail... maskEnums) {
      for(NKNDetail m : maskEnums) {
        if(!m.isEnabled(affectNewKeyboard)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectNewKeyboardEnable(NKNDetail... maskEnums) {
      for(NKNDetail m : maskEnums) {
        affectNewKeyboard((short) m.enableFor(affectNewKeyboard));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectNewKeyboardDisable(NKNDetail... maskEnums) {
      for(NKNDetail m : maskEnums) {
        affectNewKeyboard((short) m.disableFor(affectNewKeyboard));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder newKeyboardDetails(short newKeyboardDetails) {
      this.newKeyboardDetails = newKeyboardDetails;
      affectWhichEnable(EventType.NEW_KEYBOARD_NOTIFY);
      return this;
    }

    public boolean isNewKeyboardDetailsEnabled(@NonNull NKNDetail... maskEnums) {
      for(NKNDetail m : maskEnums) {
        if(!m.isEnabled(newKeyboardDetails)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder newKeyboardDetailsEnable(NKNDetail... maskEnums) {
      for(NKNDetail m : maskEnums) {
        newKeyboardDetails((short) m.enableFor(newKeyboardDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder newKeyboardDetailsDisable(NKNDetail... maskEnums) {
      for(NKNDetail m : maskEnums) {
        newKeyboardDetails((short) m.disableFor(newKeyboardDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectState(short affectState) {
      this.affectState = affectState;
      affectWhichEnable(EventType.STATE_NOTIFY);
      return this;
    }

    public boolean isAffectStateEnabled(@NonNull StatePart... maskEnums) {
      for(StatePart m : maskEnums) {
        if(!m.isEnabled(affectState)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectStateEnable(StatePart... maskEnums) {
      for(StatePart m : maskEnums) {
        affectState((short) m.enableFor(affectState));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectStateDisable(StatePart... maskEnums) {
      for(StatePart m : maskEnums) {
        affectState((short) m.disableFor(affectState));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder stateDetails(short stateDetails) {
      this.stateDetails = stateDetails;
      affectWhichEnable(EventType.STATE_NOTIFY);
      return this;
    }

    public boolean isStateDetailsEnabled(@NonNull StatePart... maskEnums) {
      for(StatePart m : maskEnums) {
        if(!m.isEnabled(stateDetails)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder stateDetailsEnable(StatePart... maskEnums) {
      for(StatePart m : maskEnums) {
        stateDetails((short) m.enableFor(stateDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder stateDetailsDisable(StatePart... maskEnums) {
      for(StatePart m : maskEnums) {
        stateDetails((short) m.disableFor(stateDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectCtrls(int affectCtrls) {
      this.affectCtrls = affectCtrls;
      affectWhichEnable(EventType.CONTROLS_NOTIFY);
      return this;
    }

    public boolean isAffectCtrlsEnabled(@NonNull Control... maskEnums) {
      for(Control m : maskEnums) {
        if(!m.isEnabled(affectCtrls)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectCtrlsEnable(Control... maskEnums) {
      for(Control m : maskEnums) {
        affectCtrls((int) m.enableFor(affectCtrls));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectCtrlsDisable(Control... maskEnums) {
      for(Control m : maskEnums) {
        affectCtrls((int) m.disableFor(affectCtrls));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder ctrlDetails(int ctrlDetails) {
      this.ctrlDetails = ctrlDetails;
      affectWhichEnable(EventType.CONTROLS_NOTIFY);
      return this;
    }

    public boolean isCtrlDetailsEnabled(@NonNull Control... maskEnums) {
      for(Control m : maskEnums) {
        if(!m.isEnabled(ctrlDetails)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder ctrlDetailsEnable(Control... maskEnums) {
      for(Control m : maskEnums) {
        ctrlDetails((int) m.enableFor(ctrlDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder ctrlDetailsDisable(Control... maskEnums) {
      for(Control m : maskEnums) {
        ctrlDetails((int) m.disableFor(ctrlDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectIndicatorState(int affectIndicatorState) {
      this.affectIndicatorState = affectIndicatorState;
      affectWhichEnable(EventType.INDICATOR_STATE_NOTIFY);
      return this;
    }

    public SelectEvents.SelectEventsBuilder indicatorStateDetails(int indicatorStateDetails) {
      this.indicatorStateDetails = indicatorStateDetails;
      affectWhichEnable(EventType.INDICATOR_STATE_NOTIFY);
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectIndicatorMap(int affectIndicatorMap) {
      this.affectIndicatorMap = affectIndicatorMap;
      affectWhichEnable(EventType.INDICATOR_MAP_NOTIFY);
      return this;
    }

    public SelectEvents.SelectEventsBuilder indicatorMapDetails(int indicatorMapDetails) {
      this.indicatorMapDetails = indicatorMapDetails;
      affectWhichEnable(EventType.INDICATOR_MAP_NOTIFY);
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectNames(short affectNames) {
      this.affectNames = affectNames;
      affectWhichEnable(EventType.NAMES_NOTIFY);
      return this;
    }

    public boolean isAffectNamesEnabled(@NonNull NameDetail... maskEnums) {
      for(NameDetail m : maskEnums) {
        if(!m.isEnabled(affectNames)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectNamesEnable(NameDetail... maskEnums) {
      for(NameDetail m : maskEnums) {
        affectNames((short) m.enableFor(affectNames));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectNamesDisable(NameDetail... maskEnums) {
      for(NameDetail m : maskEnums) {
        affectNames((short) m.disableFor(affectNames));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder namesDetails(short namesDetails) {
      this.namesDetails = namesDetails;
      affectWhichEnable(EventType.NAMES_NOTIFY);
      return this;
    }

    public boolean isNamesDetailsEnabled(@NonNull NameDetail... maskEnums) {
      for(NameDetail m : maskEnums) {
        if(!m.isEnabled(namesDetails)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder namesDetailsEnable(NameDetail... maskEnums) {
      for(NameDetail m : maskEnums) {
        namesDetails((short) m.enableFor(namesDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder namesDetailsDisable(NameDetail... maskEnums) {
      for(NameDetail m : maskEnums) {
        namesDetails((short) m.disableFor(namesDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectCompat(byte affectCompat) {
      this.affectCompat = affectCompat;
      affectWhichEnable(EventType.COMPAT_MAP_NOTIFY);
      return this;
    }

    public boolean isAffectCompatEnabled(@NonNull CMDetail... maskEnums) {
      for(CMDetail m : maskEnums) {
        if(!m.isEnabled(affectCompat)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectCompatEnable(CMDetail... maskEnums) {
      for(CMDetail m : maskEnums) {
        affectCompat((byte) m.enableFor(affectCompat));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectCompatDisable(CMDetail... maskEnums) {
      for(CMDetail m : maskEnums) {
        affectCompat((byte) m.disableFor(affectCompat));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder compatDetails(byte compatDetails) {
      this.compatDetails = compatDetails;
      affectWhichEnable(EventType.COMPAT_MAP_NOTIFY);
      return this;
    }

    public boolean isCompatDetailsEnabled(@NonNull CMDetail... maskEnums) {
      for(CMDetail m : maskEnums) {
        if(!m.isEnabled(compatDetails)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder compatDetailsEnable(CMDetail... maskEnums) {
      for(CMDetail m : maskEnums) {
        compatDetails((byte) m.enableFor(compatDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder compatDetailsDisable(CMDetail... maskEnums) {
      for(CMDetail m : maskEnums) {
        compatDetails((byte) m.disableFor(compatDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectBell(byte affectBell) {
      this.affectBell = affectBell;
      affectWhichEnable(EventType.BELL_NOTIFY);
      return this;
    }

    public SelectEvents.SelectEventsBuilder bellDetails(byte bellDetails) {
      this.bellDetails = bellDetails;
      affectWhichEnable(EventType.BELL_NOTIFY);
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectMsgDetails(byte affectMsgDetails) {
      this.affectMsgDetails = affectMsgDetails;
      affectWhichEnable(EventType.ACTION_MESSAGE);
      return this;
    }

    public SelectEvents.SelectEventsBuilder msgDetails(byte msgDetails) {
      this.msgDetails = msgDetails;
      affectWhichEnable(EventType.ACTION_MESSAGE);
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectAccessX(short affectAccessX) {
      this.affectAccessX = affectAccessX;
      affectWhichEnable(EventType.ACCESS_X_NOTIFY);
      return this;
    }

    public boolean isAffectAccessXEnabled(@NonNull AXNDetail... maskEnums) {
      for(AXNDetail m : maskEnums) {
        if(!m.isEnabled(affectAccessX)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectAccessXEnable(AXNDetail... maskEnums) {
      for(AXNDetail m : maskEnums) {
        affectAccessX((short) m.enableFor(affectAccessX));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectAccessXDisable(AXNDetail... maskEnums) {
      for(AXNDetail m : maskEnums) {
        affectAccessX((short) m.disableFor(affectAccessX));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder accessXDetails(short accessXDetails) {
      this.accessXDetails = accessXDetails;
      affectWhichEnable(EventType.ACCESS_X_NOTIFY);
      return this;
    }

    public boolean isAccessXDetailsEnabled(@NonNull AXNDetail... maskEnums) {
      for(AXNDetail m : maskEnums) {
        if(!m.isEnabled(accessXDetails)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder accessXDetailsEnable(AXNDetail... maskEnums) {
      for(AXNDetail m : maskEnums) {
        accessXDetails((short) m.enableFor(accessXDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder accessXDetailsDisable(AXNDetail... maskEnums) {
      for(AXNDetail m : maskEnums) {
        accessXDetails((short) m.disableFor(accessXDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectExtDev(short affectExtDev) {
      this.affectExtDev = affectExtDev;
      affectWhichEnable(EventType.EXTENSION_DEVICE_NOTIFY);
      return this;
    }

    public boolean isAffectExtDevEnabled(@NonNull XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        if(!m.isEnabled(affectExtDev)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectExtDevEnable(XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        affectExtDev((short) m.enableFor(affectExtDev));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectExtDevDisable(XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        affectExtDev((short) m.disableFor(affectExtDev));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder extdevDetails(short extdevDetails) {
      this.extdevDetails = extdevDetails;
      affectWhichEnable(EventType.EXTENSION_DEVICE_NOTIFY);
      return this;
    }

    public boolean isExtdevDetailsEnabled(@NonNull XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        if(!m.isEnabled(extdevDetails)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder extdevDetailsEnable(XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        extdevDetails((short) m.enableFor(extdevDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder extdevDetailsDisable(XIFeature... maskEnums) {
      for(XIFeature m : maskEnums) {
        extdevDetails((short) m.disableFor(extdevDetails));
      }
      return this;
    }

    public int getSize() {
      return 16 + (EventType.NEW_KEYBOARD_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.NEW_KEYBOARD_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.CONTROLS_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.CONTROLS_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.INDICATOR_STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.INDICATOR_STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.INDICATOR_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.INDICATOR_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.NAMES_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.NAMES_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.COMPAT_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.COMPAT_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.BELL_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.BELL_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.ACTION_MESSAGE.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.ACTION_MESSAGE.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.ACCESS_X_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.ACCESS_X_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.EXTENSION_DEVICE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.EXTENSION_DEVICE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~ (Short.toUnsignedInt(clear))) & (~ (Short.toUnsignedInt(selectAll))))) ? 2 : 0);
    }
  }
}
