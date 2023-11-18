package com.github.moaxcp.x11client.protocol.shm;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CompletionEvent implements XEvent, ShmObject {
  public static final byte NUMBER = 0;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private int drawable;

  private short minorEvent;

  private byte majorEvent;

  private int shmseg;

  private int offset;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static CompletionEvent readCompletionEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    CompletionEvent.CompletionEventBuilder javaBuilder = CompletionEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int drawable = in.readCard32();
    short minorEvent = in.readCard16();
    byte majorEvent = in.readByte();
    byte[] pad6 = in.readPad(1);
    int shmseg = in.readCard32();
    int offset = in.readCard32();
    byte[] pad9 = in.readPad(12);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.drawable(drawable);
    javaBuilder.minorEvent(minorEvent);
    javaBuilder.majorEvent(majorEvent);
    javaBuilder.shmseg(shmseg);
    javaBuilder.offset(offset);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(drawable);
    out.writeCard16(minorEvent);
    out.writeByte(majorEvent);
    out.writePad(1);
    out.writeCard32(shmseg);
    out.writeCard32(offset);
    out.writePad(12);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class CompletionEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
