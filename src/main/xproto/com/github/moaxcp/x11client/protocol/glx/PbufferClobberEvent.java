package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PbufferClobberEvent implements XEvent, GlxObject {
  public static final byte NUMBER = 0;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private short eventType;

  private short drawType;

  private int drawable;

  private int bMask;

  private short auxBuffer;

  private short x;

  private short y;

  private short width;

  private short height;

  private short count;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static PbufferClobberEvent readPbufferClobberEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    PbufferClobberEvent.PbufferClobberEventBuilder javaBuilder = PbufferClobberEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    short eventType = in.readCard16();
    short drawType = in.readCard16();
    int drawable = in.readCard32();
    int bMask = in.readCard32();
    short auxBuffer = in.readCard16();
    short x = in.readCard16();
    short y = in.readCard16();
    short width = in.readCard16();
    short height = in.readCard16();
    short count = in.readCard16();
    byte[] pad13 = in.readPad(4);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
    javaBuilder.drawType(drawType);
    javaBuilder.drawable(drawable);
    javaBuilder.bMask(bMask);
    javaBuilder.auxBuffer(auxBuffer);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.count(count);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard16(eventType);
    out.writeCard16(drawType);
    out.writeCard32(drawable);
    out.writeCard32(bMask);
    out.writeCard16(auxBuffer);
    out.writeCard16(x);
    out.writeCard16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(count);
    out.writePad(4);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class PbufferClobberEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
