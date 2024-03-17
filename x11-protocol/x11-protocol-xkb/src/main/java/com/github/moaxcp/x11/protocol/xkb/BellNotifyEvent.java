package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BellNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte NUMBER = 8;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte xkbType;

  private short sequenceNumber;

  private int time;

  private byte deviceID;

  private byte bellClass;

  private byte bellID;

  private byte percent;

  private short pitch;

  private short duration;

  private int name;

  private int window;

  private boolean eventOnly;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static BellNotifyEvent readBellNotifyEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    BellNotifyEvent.BellNotifyEventBuilder javaBuilder = BellNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte bellClass = in.readCard8();
    byte bellID = in.readCard8();
    byte percent = in.readCard8();
    short pitch = in.readCard16();
    short duration = in.readCard16();
    int name = in.readCard32();
    int window = in.readCard32();
    boolean eventOnly = in.readBool();
    byte[] pad13 = in.readPad(7);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.bellClass(bellClass);
    javaBuilder.bellID(bellID);
    javaBuilder.percent(percent);
    javaBuilder.pitch(pitch);
    javaBuilder.duration(duration);
    javaBuilder.name(name);
    javaBuilder.window(window);
    javaBuilder.eventOnly(eventOnly);

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
    out.writeCard8(bellClass);
    out.writeCard8(bellID);
    out.writeCard8(percent);
    out.writeCard16(pitch);
    out.writeCard16(duration);
    out.writeCard32(name);
    out.writeCard32(window);
    out.writeBool(eventOnly);
    out.writePad(7);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class BellNotifyEventBuilder {
    public BellNotifyEvent.BellNotifyEventBuilder bellClass(BellClassResult bellClass) {
      this.bellClass = (byte) bellClass.getValue();
      return this;
    }

    public BellNotifyEvent.BellNotifyEventBuilder bellClass(byte bellClass) {
      this.bellClass = bellClass;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
