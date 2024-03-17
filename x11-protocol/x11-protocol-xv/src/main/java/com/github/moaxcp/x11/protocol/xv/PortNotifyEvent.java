package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PortNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xv";

  public static final byte NUMBER = 1;

  private byte firstEventOffset;

  private boolean sentEvent;

  private short sequenceNumber;

  private int time;

  private int port;

  private int attribute;

  private int value;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static PortNotifyEvent readPortNotifyEvent(byte firstEventOffset, boolean sentEvent,
      X11Input in) throws IOException {
    PortNotifyEvent.PortNotifyEventBuilder javaBuilder = PortNotifyEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    int port = in.readCard32();
    int attribute = in.readCard32();
    int value = in.readInt32();
    byte[] pad7 = in.readPad(12);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.port(port);
    javaBuilder.attribute(attribute);
    javaBuilder.value(value);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 | getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard32(port);
    out.writeCard32(attribute);
    out.writeInt32(value);
    out.writePad(12);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PortNotifyEventBuilder {
    public int getSize() {
      return 32;
    }
  }
}
