package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CirculateRequestEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte NUMBER = 26;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private int event;

  private int window;

  private byte place;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static CirculateRequestEvent readCirculateRequestEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    CirculateRequestEvent.CirculateRequestEventBuilder javaBuilder = CirculateRequestEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int event = in.readCard32();
    int window = in.readCard32();
    byte[] pad5 = in.readPad(4);
    byte place = in.readByte();
    byte[] pad7 = in.readPad(3);
    byte[] pad8 = in.readPad(12);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.event(event);
    javaBuilder.window(window);
    javaBuilder.place(place);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(event);
    out.writeCard32(window);
    out.writePad(4);
    out.writeByte(place);
    out.writePad(3);
    out.writePad(12);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CirculateRequestEventBuilder {
    public CirculateRequestEvent.CirculateRequestEventBuilder place(Place place) {
      this.place = (byte) place.getValue();
      return this;
    }

    public CirculateRequestEvent.CirculateRequestEventBuilder place(byte place) {
      this.place = place;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
