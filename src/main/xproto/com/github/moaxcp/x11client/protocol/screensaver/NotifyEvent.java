package com.github.moaxcp.x11client.protocol.screensaver;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NotifyEvent implements XEvent, ScreensaverObject {
  public static final byte NUMBER = 0;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte state;

  private short sequenceNumber;

  private int time;

  private int root;

  private int window;

  private byte kind;

  private boolean forced;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static NotifyEvent readNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in)
      throws IOException {
    NotifyEvent.NotifyEventBuilder javaBuilder = NotifyEvent.builder();
    byte state = in.readByte();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    int root = in.readCard32();
    int window = in.readCard32();
    byte kind = in.readByte();
    boolean forced = in.readBool();
    byte[] pad8 = in.readPad(14);
    javaBuilder.state(state);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.root(root);
    javaBuilder.window(window);
    javaBuilder.kind(kind);
    javaBuilder.forced(forced);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeByte(state);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard32(root);
    out.writeCard32(window);
    out.writeByte(kind);
    out.writeBool(forced);
    out.writePad(14);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class NotifyEventBuilder {
    public NotifyEvent.NotifyEventBuilder state(State state) {
      this.state = (byte) state.getValue();
      return this;
    }

    public NotifyEvent.NotifyEventBuilder state(byte state) {
      this.state = state;
      return this;
    }

    public NotifyEvent.NotifyEventBuilder kind(Kind kind) {
      this.kind = (byte) kind.getValue();
      return this;
    }

    public NotifyEvent.NotifyEventBuilder kind(byte kind) {
      this.kind = kind;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
