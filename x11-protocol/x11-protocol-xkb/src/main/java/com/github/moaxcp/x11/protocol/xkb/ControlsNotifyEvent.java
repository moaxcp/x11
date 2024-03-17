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
public class ControlsNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte NUMBER = 3;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte xkbType;

  private short sequenceNumber;

  private int time;

  private byte deviceID;

  private byte numGroups;

  private int changedControls;

  private int enabledControls;

  private int enabledControlChanges;

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

  public static ControlsNotifyEvent readControlsNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    ControlsNotifyEvent.ControlsNotifyEventBuilder javaBuilder = ControlsNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte numGroups = in.readCard8();
    byte[] pad6 = in.readPad(2);
    int changedControls = in.readCard32();
    int enabledControls = in.readCard32();
    int enabledControlChanges = in.readCard32();
    byte keycode = in.readCard8();
    byte eventType = in.readCard8();
    byte requestMajor = in.readCard8();
    byte requestMinor = in.readCard8();
    byte[] pad14 = in.readPad(4);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.numGroups(numGroups);
    javaBuilder.changedControls(changedControls);
    javaBuilder.enabledControls(enabledControls);
    javaBuilder.enabledControlChanges(enabledControlChanges);
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
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writeCard8(xkbType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard8(deviceID);
    out.writeCard8(numGroups);
    out.writePad(2);
    out.writeCard32(changedControls);
    out.writeCard32(enabledControls);
    out.writeCard32(enabledControlChanges);
    out.writeCard8(keycode);
    out.writeCard8(eventType);
    out.writeCard8(requestMajor);
    out.writeCard8(requestMinor);
    out.writePad(4);
  }

  public boolean isChangedControlsEnabled(@NonNull Control... maskEnums) {
    for(Control m : maskEnums) {
      if(!m.isEnabled(changedControls)) {
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

  public boolean isEnabledControlChangesEnabled(@NonNull BoolCtrl... maskEnums) {
    for(BoolCtrl m : maskEnums) {
      if(!m.isEnabled(enabledControlChanges)) {
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

  public static class ControlsNotifyEventBuilder {
    public boolean isChangedControlsEnabled(@NonNull Control... maskEnums) {
      for(Control m : maskEnums) {
        if(!m.isEnabled(changedControls)) {
          return false;
        }
      }
      return true;
    }

    public ControlsNotifyEvent.ControlsNotifyEventBuilder changedControlsEnable(
        Control... maskEnums) {
      for(Control m : maskEnums) {
        changedControls((int) m.enableFor(changedControls));
      }
      return this;
    }

    public ControlsNotifyEvent.ControlsNotifyEventBuilder changedControlsDisable(
        Control... maskEnums) {
      for(Control m : maskEnums) {
        changedControls((int) m.disableFor(changedControls));
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

    public ControlsNotifyEvent.ControlsNotifyEventBuilder enabledControlsEnable(
        BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        enabledControls((int) m.enableFor(enabledControls));
      }
      return this;
    }

    public ControlsNotifyEvent.ControlsNotifyEventBuilder enabledControlsDisable(
        BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        enabledControls((int) m.disableFor(enabledControls));
      }
      return this;
    }

    public boolean isEnabledControlChangesEnabled(@NonNull BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        if(!m.isEnabled(enabledControlChanges)) {
          return false;
        }
      }
      return true;
    }

    public ControlsNotifyEvent.ControlsNotifyEventBuilder enabledControlChangesEnable(
        BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        enabledControlChanges((int) m.enableFor(enabledControlChanges));
      }
      return this;
    }

    public ControlsNotifyEvent.ControlsNotifyEventBuilder enabledControlChangesDisable(
        BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        enabledControlChanges((int) m.disableFor(enabledControlChanges));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
