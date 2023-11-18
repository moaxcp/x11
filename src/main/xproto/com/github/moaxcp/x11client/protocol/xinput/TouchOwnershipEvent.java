package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TouchOwnershipEvent implements XGenericEvent, XinputObject {
  public static final byte NUMBER = 35;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte extension;

  private short sequenceNumber;

  private short eventType;

  private short deviceid;

  private int time;

  private int touchid;

  private int root;

  private int event;

  private int child;

  private short sourceid;

  private int flags;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static TouchOwnershipEvent readTouchOwnershipEvent(byte firstEventOffset,
      boolean sentEvent, byte extension, short sequenceNumber, int length, short eventType,
      X11Input in) throws IOException {
    TouchOwnershipEvent.TouchOwnershipEventBuilder javaBuilder = TouchOwnershipEvent.builder();
    short deviceid = in.readCard16();
    int time = in.readCard32();
    int touchid = in.readCard32();
    int root = in.readCard32();
    int event = in.readCard32();
    int child = in.readCard32();
    short sourceid = in.readCard16();
    byte[] pad12 = in.readPad(2);
    int flags = in.readCard32();
    byte[] pad14 = in.readPad(8);
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
    javaBuilder.deviceid(deviceid);
    javaBuilder.time(time);
    javaBuilder.touchid(touchid);
    javaBuilder.root(root);
    javaBuilder.event(event);
    javaBuilder.child(child);
    javaBuilder.sourceid(sourceid);
    javaBuilder.flags(flags);

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
    out.writeCard16(deviceid);
    out.writeCard32(time);
    out.writeCard32(touchid);
    out.writeCard32(root);
    out.writeCard32(event);
    out.writeCard32(child);
    out.writeCard16(sourceid);
    out.writePad(2);
    out.writeCard32(flags);
    out.writePad(8);
  }

  @Override
  public int getSize() {
    return 48;
  }

  public static class TouchOwnershipEventBuilder {
    public TouchOwnershipEvent.TouchOwnershipEventBuilder flags(TouchOwnershipFlags flags) {
      this.flags = (int) flags.getValue();
      return this;
    }

    public TouchOwnershipEvent.TouchOwnershipEventBuilder flags(int flags) {
      this.flags = flags;
      return this;
    }

    public int getSize() {
      return 48;
    }
  }
}
