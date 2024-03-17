package com.github.moaxcp.x11.protocol.dri2;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InvalidateBuffersEvent implements XEvent {
  public static final String PLUGIN_NAME = "dri2";

  public static final byte NUMBER = 1;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private int drawable;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static InvalidateBuffersEvent readInvalidateBuffersEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    InvalidateBuffersEvent.InvalidateBuffersEventBuilder javaBuilder = InvalidateBuffersEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int drawable = in.readCard32();
    byte[] pad4 = in.readPad(24);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.drawable(drawable);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(drawable);
    out.writePad(24);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class InvalidateBuffersEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
