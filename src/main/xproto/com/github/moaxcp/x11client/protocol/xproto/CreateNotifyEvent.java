package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateNotifyEvent implements XEvent, XprotoObject {
  public static final byte NUMBER = 16;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private int parent;

  private int window;

  private short x;

  private short y;

  private short width;

  private short height;

  private short borderWidth;

  private boolean overrideRedirect;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static CreateNotifyEvent readCreateNotifyEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    CreateNotifyEvent.CreateNotifyEventBuilder javaBuilder = CreateNotifyEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int parent = in.readCard32();
    int window = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    short borderWidth = in.readCard16();
    boolean overrideRedirect = in.readBool();
    byte[] pad11 = in.readPad(1);
    byte[] pad12 = in.readPad(8);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.parent(parent);
    javaBuilder.window(window);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.borderWidth(borderWidth);
    javaBuilder.overrideRedirect(overrideRedirect);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(parent);
    out.writeCard32(window);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(borderWidth);
    out.writeBool(overrideRedirect);
    out.writePad(1);
    out.writePad(8);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class CreateNotifyEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
