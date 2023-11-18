package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NotifyEvent implements XEvent, XprintObject {
  public static final byte NUMBER = 0;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte detail;

  private short sequenceNumber;

  private int context;

  private boolean cancel;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static NotifyEvent readNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in)
      throws IOException {
    NotifyEvent.NotifyEventBuilder javaBuilder = NotifyEvent.builder();
    byte detail = in.readCard8();
    short sequenceNumber = in.readCard16();
    int context = in.readCard32();
    boolean cancel = in.readBool();
    byte[] pad5 = in.readPad(23);
    javaBuilder.detail(detail);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.context(context);
    javaBuilder.cancel(cancel);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(detail);
    out.writeCard16(sequenceNumber);
    out.writeCard32(context);
    out.writeBool(cancel);
    out.writePad(23);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class NotifyEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
