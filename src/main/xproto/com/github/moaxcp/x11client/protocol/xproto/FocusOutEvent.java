package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FocusOutEvent implements XEvent, XprotoObject {
  public static final byte NUMBER = 9;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte detail;

  private short sequenceNumber;

  private int event;

  private byte mode;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static FocusOutEvent readFocusOutEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    FocusOutEvent.FocusOutEventBuilder javaBuilder = FocusOutEvent.builder();
    byte detail = in.readByte();
    short sequenceNumber = in.readCard16();
    int event = in.readCard32();
    byte mode = in.readByte();
    byte[] pad5 = in.readPad(3);
    byte[] pad6 = in.readPad(20);
    javaBuilder.detail(detail);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.event(event);
    javaBuilder.mode(mode);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeByte(detail);
    out.writeCard16(sequenceNumber);
    out.writeCard32(event);
    out.writeByte(mode);
    out.writePad(3);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class FocusOutEventBuilder {
    public FocusOutEvent.FocusOutEventBuilder detail(NotifyDetail detail) {
      this.detail = (byte) detail.getValue();
      return this;
    }

    public FocusOutEvent.FocusOutEventBuilder detail(byte detail) {
      this.detail = detail;
      return this;
    }

    public FocusOutEvent.FocusOutEventBuilder mode(NotifyMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public FocusOutEvent.FocusOutEventBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
