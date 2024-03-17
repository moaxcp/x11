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
public class NewKeyboardNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte NUMBER = 0;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte xkbType;

  private short sequenceNumber;

  private int time;

  private byte deviceID;

  private byte oldDeviceID;

  private byte minKeyCode;

  private byte maxKeyCode;

  private byte oldMinKeyCode;

  private byte oldMaxKeyCode;

  private byte requestMajor;

  private byte requestMinor;

  private short changed;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static NewKeyboardNotifyEvent readNewKeyboardNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder javaBuilder = NewKeyboardNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte oldDeviceID = in.readCard8();
    byte minKeyCode = in.readCard8();
    byte maxKeyCode = in.readCard8();
    byte oldMinKeyCode = in.readCard8();
    byte oldMaxKeyCode = in.readCard8();
    byte requestMajor = in.readCard8();
    byte requestMinor = in.readCard8();
    short changed = in.readCard16();
    byte[] pad13 = in.readPad(14);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.oldDeviceID(oldDeviceID);
    javaBuilder.minKeyCode(minKeyCode);
    javaBuilder.maxKeyCode(maxKeyCode);
    javaBuilder.oldMinKeyCode(oldMinKeyCode);
    javaBuilder.oldMaxKeyCode(oldMaxKeyCode);
    javaBuilder.requestMajor(requestMajor);
    javaBuilder.requestMinor(requestMinor);
    javaBuilder.changed(changed);

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
    out.writeCard8(oldDeviceID);
    out.writeCard8(minKeyCode);
    out.writeCard8(maxKeyCode);
    out.writeCard8(oldMinKeyCode);
    out.writeCard8(oldMaxKeyCode);
    out.writeCard8(requestMajor);
    out.writeCard8(requestMinor);
    out.writeCard16(changed);
    out.writePad(14);
  }

  public boolean isChangedEnabled(@NonNull NKNDetail... maskEnums) {
    for(NKNDetail m : maskEnums) {
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class NewKeyboardNotifyEventBuilder {
    public boolean isChangedEnabled(@NonNull NKNDetail... maskEnums) {
      for(NKNDetail m : maskEnums) {
        if(!m.isEnabled(changed)) {
          return false;
        }
      }
      return true;
    }

    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder changedEnable(
        NKNDetail... maskEnums) {
      for(NKNDetail m : maskEnums) {
        changed((short) m.enableFor(changed));
      }
      return this;
    }

    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder changedDisable(
        NKNDetail... maskEnums) {
      for(NKNDetail m : maskEnums) {
        changed((short) m.disableFor(changed));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
