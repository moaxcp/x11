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
public class AccessXNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte NUMBER = 10;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte xkbType;

  private short sequenceNumber;

  private int time;

  private byte deviceID;

  private byte keycode;

  private short detailt;

  private short slowKeysDelay;

  private short debounceDelay;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static AccessXNotifyEvent readAccessXNotifyEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    AccessXNotifyEvent.AccessXNotifyEventBuilder javaBuilder = AccessXNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte keycode = in.readCard8();
    short detailt = in.readCard16();
    short slowKeysDelay = in.readCard16();
    short debounceDelay = in.readCard16();
    byte[] pad9 = in.readPad(16);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.keycode(keycode);
    javaBuilder.detailt(detailt);
    javaBuilder.slowKeysDelay(slowKeysDelay);
    javaBuilder.debounceDelay(debounceDelay);

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
    out.writeCard8(keycode);
    out.writeCard16(detailt);
    out.writeCard16(slowKeysDelay);
    out.writeCard16(debounceDelay);
    out.writePad(16);
  }

  public boolean isDetailtEnabled(@NonNull AXNDetail... maskEnums) {
    for(AXNDetail m : maskEnums) {
      if(!m.isEnabled(detailt)) {
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

  public static class AccessXNotifyEventBuilder {
    public boolean isDetailtEnabled(@NonNull AXNDetail... maskEnums) {
      for(AXNDetail m : maskEnums) {
        if(!m.isEnabled(detailt)) {
          return false;
        }
      }
      return true;
    }

    public AccessXNotifyEvent.AccessXNotifyEventBuilder detailtEnable(AXNDetail... maskEnums) {
      for(AXNDetail m : maskEnums) {
        detailt((short) m.enableFor(detailt));
      }
      return this;
    }

    public AccessXNotifyEvent.AccessXNotifyEventBuilder detailtDisable(AXNDetail... maskEnums) {
      for(AXNDetail m : maskEnums) {
        detailt((short) m.disableFor(detailt));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
