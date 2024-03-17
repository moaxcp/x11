package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AttributNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xprint";

  public static final byte NUMBER = 1;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte detail;

  private short sequenceNumber;

  private int context;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static AttributNotifyEvent readAttributNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    AttributNotifyEvent.AttributNotifyEventBuilder javaBuilder = AttributNotifyEvent.builder();
    byte detail = in.readCard8();
    short sequenceNumber = in.readCard16();
    int context = in.readCard32();
    byte[] pad4 = in.readPad(24);
    javaBuilder.detail(detail);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.context(context);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writeCard8(detail);
    out.writeCard16(sequenceNumber);
    out.writeCard32(context);
    out.writePad(24);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class AttributNotifyEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
