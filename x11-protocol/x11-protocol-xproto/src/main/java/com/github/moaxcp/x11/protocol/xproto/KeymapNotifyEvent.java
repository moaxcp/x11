package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class KeymapNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte NUMBER = 11;

  private byte firstEventOffset;

  private boolean sentEvent;

  @NonNull
  private List<Byte> keys;

  private short sequenceNumber;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static KeymapNotifyEvent readKeymapNotifyEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    KeymapNotifyEvent.KeymapNotifyEventBuilder javaBuilder = KeymapNotifyEvent.builder();
    List<Byte> keys = in.readCard8(31);
    short sequenceNumber = in.readCard16();
    javaBuilder.keys(keys);
    javaBuilder.sequenceNumber(sequenceNumber);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writeCard8(keys);
    out.writeCard16(sequenceNumber);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 3 + 1 * keys.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class KeymapNotifyEventBuilder {
    public int getSize() {
      return 3 + 1 * keys.size();
    }
  }
}
