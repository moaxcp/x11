package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SelectionClearEvent implements XEvent, XprotoObject {
  public static final byte NUMBER = 29;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private int time;

  private int owner;

  private int selection;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static SelectionClearEvent readSelectionClearEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    SelectionClearEvent.SelectionClearEventBuilder javaBuilder = SelectionClearEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    int owner = in.readCard32();
    int selection = in.readCard32();
    byte[] pad6 = in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.owner(owner);
    javaBuilder.selection(selection);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard32(owner);
    out.writeCard32(selection);
    out.writePad(16);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class SelectionClearEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
