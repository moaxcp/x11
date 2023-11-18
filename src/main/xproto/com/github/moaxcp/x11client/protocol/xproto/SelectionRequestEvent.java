package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SelectionRequestEvent implements XEvent, XprotoObject {
  public static final byte NUMBER = 30;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private int time;

  private int owner;

  private int requestor;

  private int selection;

  private int target;

  private int property;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static SelectionRequestEvent readSelectionRequestEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    SelectionRequestEvent.SelectionRequestEventBuilder javaBuilder = SelectionRequestEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    int owner = in.readCard32();
    int requestor = in.readCard32();
    int selection = in.readCard32();
    int target = in.readCard32();
    int property = in.readCard32();
    byte[] pad9 = in.readPad(4);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.owner(owner);
    javaBuilder.requestor(requestor);
    javaBuilder.selection(selection);
    javaBuilder.target(target);
    javaBuilder.property(property);

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
    out.writeCard32(requestor);
    out.writeCard32(selection);
    out.writeCard32(target);
    out.writeCard32(property);
    out.writePad(4);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class SelectionRequestEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
