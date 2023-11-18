package com.github.moaxcp.x11client.protocol.present;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class IdleNotifyEvent implements XGenericEvent, PresentObject {
  public static final byte NUMBER = 35;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte extension;

  private short sequenceNumber;

  private short eventType;

  private int event;

  private int window;

  private int serial;

  private int pixmap;

  private int idleFence;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static IdleNotifyEvent readIdleNotifyEvent(byte firstEventOffset, boolean sentEvent,
      byte extension, short sequenceNumber, int length, short eventType, X11Input in) throws
      IOException {
    IdleNotifyEvent.IdleNotifyEventBuilder javaBuilder = IdleNotifyEvent.builder();
    byte[] pad5 = in.readPad(2);
    int event = in.readCard32();
    int window = in.readCard32();
    int serial = in.readCard32();
    int pixmap = in.readCard32();
    int idleFence = in.readCard32();
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
    javaBuilder.event(event);
    javaBuilder.window(window);
    javaBuilder.serial(serial);
    javaBuilder.pixmap(pixmap);
    javaBuilder.idleFence(idleFence);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(extension);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength() - 32);
    out.writeCard16(eventType);
    out.writePad(2);
    out.writeCard32(event);
    out.writeCard32(window);
    out.writeCard32(serial);
    out.writeCard32(pixmap);
    out.writeCard32(idleFence);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class IdleNotifyEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
