package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ColormapNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte NUMBER = 32;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private int window;

  private int colormap;

  private boolean newValue;

  private byte state;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ColormapNotifyEvent readColormapNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    ColormapNotifyEvent.ColormapNotifyEventBuilder javaBuilder = ColormapNotifyEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int window = in.readCard32();
    int colormap = in.readCard32();
    boolean newValue = in.readBool();
    byte state = in.readByte();
    byte[] pad7 = in.readPad(2);
    byte[] pad8 = in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.window(window);
    javaBuilder.colormap(colormap);
    javaBuilder.newValue(newValue);
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
    out.writeCard32(colormap);
    out.writeBool(newValue);
    out.writeByte(state);
    out.writePad(2);
    out.writePad(16);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ColormapNotifyEventBuilder {
    public ColormapNotifyEvent.ColormapNotifyEventBuilder state(ColormapState state) {
      this.state = (byte) state.getValue();
      return this;
    }

    public ColormapNotifyEvent.ColormapNotifyEventBuilder state(byte state) {
      this.state = state;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
