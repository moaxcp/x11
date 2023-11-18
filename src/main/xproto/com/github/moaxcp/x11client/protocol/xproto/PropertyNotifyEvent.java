package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PropertyNotifyEvent implements XEvent, XprotoObject {
  public static final byte NUMBER = 28;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private int window;

  private int atom;

  private int time;

  private byte state;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static PropertyNotifyEvent readPropertyNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    PropertyNotifyEvent.PropertyNotifyEventBuilder javaBuilder = PropertyNotifyEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int window = in.readCard32();
    int atom = in.readCard32();
    int time = in.readCard32();
    byte state = in.readByte();
    byte[] pad7 = in.readPad(3);
    byte[] pad8 = in.readPad(12);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.window(window);
    javaBuilder.atom(atom);
    javaBuilder.time(time);
    javaBuilder.state(state);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(window);
    out.writeCard32(atom);
    out.writeCard32(time);
    out.writeByte(state);
    out.writePad(3);
    out.writePad(12);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class PropertyNotifyEventBuilder {
    public PropertyNotifyEvent.PropertyNotifyEventBuilder state(Property state) {
      this.state = (byte) state.getValue();
      return this;
    }

    public PropertyNotifyEvent.PropertyNotifyEventBuilder state(byte state) {
      this.state = state;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
