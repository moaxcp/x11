package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GraphicsExposureEvent implements XEvent, XprotoObject {
  public static final byte NUMBER = 13;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private int drawable;

  private short x;

  private short y;

  private short width;

  private short height;

  private short minorOpcode;

  private short count;

  private byte majorOpcode;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static GraphicsExposureEvent readGraphicsExposureEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    GraphicsExposureEvent.GraphicsExposureEventBuilder javaBuilder = GraphicsExposureEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int drawable = in.readCard32();
    short x = in.readCard16();
    short y = in.readCard16();
    short width = in.readCard16();
    short height = in.readCard16();
    short minorOpcode = in.readCard16();
    short count = in.readCard16();
    byte majorOpcode = in.readCard8();
    byte[] pad11 = in.readPad(3);
    byte[] pad12 = in.readPad(8);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.drawable(drawable);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.minorOpcode(minorOpcode);
    javaBuilder.count(count);
    javaBuilder.majorOpcode(majorOpcode);

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
    out.writeCard16(x);
    out.writeCard16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(minorOpcode);
    out.writeCard16(count);
    out.writeCard8(majorOpcode);
    out.writePad(3);
    out.writePad(8);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class GraphicsExposureEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
