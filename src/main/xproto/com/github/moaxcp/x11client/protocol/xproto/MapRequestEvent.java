package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MapRequestEvent implements XEvent, XprotoObject {
  public static final byte NUMBER = 20;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private int parent;

  private int window;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static MapRequestEvent readMapRequestEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    MapRequestEvent.MapRequestEventBuilder javaBuilder = MapRequestEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int parent = in.readCard32();
    int window = in.readCard32();
    byte[] pad5 = in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.parent(parent);
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
    out.writeCard32(parent);
    out.writeCard32(window);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class MapRequestEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
