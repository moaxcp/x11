package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SelectionNotifyEvent implements XEvent, XfixesObject {
  public static final byte NUMBER = 0;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte subtype;

  private short sequenceNumber;

  private int window;

  private int owner;

  private int selection;

  private int timestamp;

  private int selectionTimestamp;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static SelectionNotifyEvent readSelectionNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    SelectionNotifyEvent.SelectionNotifyEventBuilder javaBuilder = SelectionNotifyEvent.builder();
    byte subtype = in.readCard8();
    short sequenceNumber = in.readCard16();
    int window = in.readCard32();
    int owner = in.readCard32();
    int selection = in.readCard32();
    int timestamp = in.readCard32();
    int selectionTimestamp = in.readCard32();
    byte[] pad8 = in.readPad(8);
    javaBuilder.subtype(subtype);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.window(window);
    javaBuilder.owner(owner);
    javaBuilder.selection(selection);
    javaBuilder.timestamp(timestamp);
    javaBuilder.selectionTimestamp(selectionTimestamp);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(subtype);
    out.writeCard16(sequenceNumber);
    out.writeCard32(window);
    out.writeCard32(owner);
    out.writeCard32(selection);
    out.writeCard32(timestamp);
    out.writeCard32(selectionTimestamp);
    out.writePad(8);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class SelectionNotifyEventBuilder {
    public SelectionNotifyEvent.SelectionNotifyEventBuilder subtype(SelectionEvent subtype) {
      this.subtype = (byte) subtype.getValue();
      return this;
    }

    public SelectionNotifyEvent.SelectionNotifyEventBuilder subtype(byte subtype) {
      this.subtype = subtype;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
