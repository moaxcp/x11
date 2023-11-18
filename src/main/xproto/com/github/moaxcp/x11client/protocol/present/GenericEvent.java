package com.github.moaxcp.x11client.protocol.present;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GenericEvent implements XEvent, PresentObject {
  public static final byte NUMBER = 0;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte extension;

  private short sequenceNumber;

  private int length;

  private short evtype;

  private int event;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static GenericEvent readGenericEvent(byte firstEventOffset, boolean sentEvent, X11Input in)
      throws IOException {
    GenericEvent.GenericEventBuilder javaBuilder = GenericEvent.builder();
    byte extension = in.readCard8();
    short sequenceNumber = in.readCard16();
    int length = in.readCard32();
    short evtype = in.readCard16();
    byte[] pad5 = in.readPad(2);
    int event = in.readCard32();
    byte[] pad7 = in.readPad(16);
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.length(length);
    javaBuilder.evtype(evtype);
    javaBuilder.event(event);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(extension);
    out.writeCard16(sequenceNumber);
    out.writeCard32(length);
    out.writeCard16(evtype);
    out.writePad(2);
    out.writeCard32(event);
    out.writePad(16);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class GenericEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
