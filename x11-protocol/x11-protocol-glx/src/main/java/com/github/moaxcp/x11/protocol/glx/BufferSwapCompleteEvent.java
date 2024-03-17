package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BufferSwapCompleteEvent implements XEvent {
  public static final String PLUGIN_NAME = "glx";

  public static final byte NUMBER = 1;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private short eventType;

  private int drawable;

  private int ustHi;

  private int ustLo;

  private int mscHi;

  private int mscLo;

  private int sbc;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static BufferSwapCompleteEvent readBufferSwapCompleteEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    BufferSwapCompleteEvent.BufferSwapCompleteEventBuilder javaBuilder = BufferSwapCompleteEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    short eventType = in.readCard16();
    byte[] pad4 = in.readPad(2);
    int drawable = in.readCard32();
    int ustHi = in.readCard32();
    int ustLo = in.readCard32();
    int mscHi = in.readCard32();
    int mscLo = in.readCard32();
    int sbc = in.readCard32();
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
    javaBuilder.drawable(drawable);
    javaBuilder.ustHi(ustHi);
    javaBuilder.ustLo(ustLo);
    javaBuilder.mscHi(mscHi);
    javaBuilder.mscLo(mscLo);
    javaBuilder.sbc(sbc);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard16(eventType);
    out.writePad(2);
    out.writeCard32(drawable);
    out.writeCard32(ustHi);
    out.writeCard32(ustLo);
    out.writeCard32(mscHi);
    out.writeCard32(mscLo);
    out.writeCard32(sbc);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class BufferSwapCompleteEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
