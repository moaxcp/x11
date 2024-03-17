package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExposeEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte NUMBER = 12;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private int window;

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

  public static ExposeEvent readExposeEvent(byte firstEventOffset, boolean sentEvent, X11Input in)
      throws IOException {
    ExposeEvent.ExposeEventBuilder javaBuilder = ExposeEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int window = in.readCard32();
    short x = in.readCard16();
    short y = in.readCard16();
    short width = in.readCard16();
    short height = in.readCard16();
    short count = in.readCard16();
    byte[] pad9 = in.readPad(2);
    byte[] pad10 = in.readPad(12);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.window(window);
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
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(window);
    out.writeCard16(x);
    out.writeCard16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(count);
    out.writePad(2);
    out.writePad(12);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ExposeEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
