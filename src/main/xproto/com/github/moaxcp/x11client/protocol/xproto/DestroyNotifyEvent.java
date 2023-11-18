package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DestroyNotifyEvent implements XEvent, XprotoObject {
  public static final byte NUMBER = 17;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private int event;

  private int window;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static DestroyNotifyEvent readDestroyNotifyEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    DestroyNotifyEvent.DestroyNotifyEventBuilder javaBuilder = DestroyNotifyEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int event = in.readCard32();
    int window = in.readCard32();
    byte[] pad5 = in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.event(event);
    javaBuilder.window(window);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(event);
    out.writeCard32(window);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class DestroyNotifyEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
