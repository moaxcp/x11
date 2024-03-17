package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CursorNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xfixes";

  public static final byte NUMBER = 1;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte subtype;

  private short sequenceNumber;

  private int window;

  private int cursorSerial;

  private int timestamp;

  private int name;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static CursorNotifyEvent readCursorNotifyEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    CursorNotifyEvent.CursorNotifyEventBuilder javaBuilder = CursorNotifyEvent.builder();
    byte subtype = in.readCard8();
    short sequenceNumber = in.readCard16();
    int window = in.readCard32();
    int cursorSerial = in.readCard32();
    int timestamp = in.readCard32();
    int name = in.readCard32();
    byte[] pad7 = in.readPad(12);
    javaBuilder.subtype(subtype);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.window(window);
    javaBuilder.cursorSerial(cursorSerial);
    javaBuilder.timestamp(timestamp);
    javaBuilder.name(name);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writeCard8(subtype);
    out.writeCard16(sequenceNumber);
    out.writeCard32(window);
    out.writeCard32(cursorSerial);
    out.writeCard32(timestamp);
    out.writeCard32(name);
    out.writePad(12);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CursorNotifyEventBuilder {
    public CursorNotifyEvent.CursorNotifyEventBuilder subtype(CursorNotify subtype) {
      this.subtype = (byte) subtype.getValue();
      return this;
    }

    public CursorNotifyEvent.CursorNotifyEventBuilder subtype(byte subtype) {
      this.subtype = subtype;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
