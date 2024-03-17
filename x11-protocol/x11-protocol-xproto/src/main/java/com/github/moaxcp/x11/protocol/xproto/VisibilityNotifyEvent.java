package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VisibilityNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte NUMBER = 15;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private int window;

  private byte state;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static VisibilityNotifyEvent readVisibilityNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    VisibilityNotifyEvent.VisibilityNotifyEventBuilder javaBuilder = VisibilityNotifyEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int window = in.readCard32();
    byte state = in.readByte();
    byte[] pad5 = in.readPad(3);
    byte[] pad6 = in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.window(window);
    javaBuilder.state(state);

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
    out.writeByte(state);
    out.writePad(3);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class VisibilityNotifyEventBuilder {
    public VisibilityNotifyEvent.VisibilityNotifyEventBuilder state(Visibility state) {
      this.state = (byte) state.getValue();
      return this;
    }

    public VisibilityNotifyEvent.VisibilityNotifyEventBuilder state(byte state) {
      this.state = state;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
