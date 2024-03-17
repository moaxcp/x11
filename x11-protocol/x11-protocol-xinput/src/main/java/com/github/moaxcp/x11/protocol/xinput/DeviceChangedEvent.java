package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XGenericEvent;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DeviceChangedEvent implements XGenericEvent {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte NUMBER = 35;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte extension;

  private short sequenceNumber;

  private short eventType;

  private short deviceid;

  private int time;

  private short sourceid;

  private byte reason;

  @NonNull
  private List<DeviceClass> classes;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static DeviceChangedEvent readDeviceChangedEvent(byte firstEventOffset, boolean sentEvent,
      byte extension, short sequenceNumber, int length, short eventType, X11Input in) throws
      IOException {
    DeviceChangedEvent.DeviceChangedEventBuilder javaBuilder = DeviceChangedEvent.builder();
    short deviceid = in.readCard16();
    int time = in.readCard32();
    short numClasses = in.readCard16();
    short sourceid = in.readCard16();
    byte reason = in.readCard8();
    byte[] pad10 = in.readPad(11);
    List<DeviceClass> classes = new ArrayList<>(Short.toUnsignedInt(numClasses));
    for(int i = 0; i < Short.toUnsignedInt(numClasses); i++) {
      classes.add(DeviceClass.readDeviceClass(in));
    }
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
    javaBuilder.deviceid(deviceid);
    javaBuilder.time(time);
    javaBuilder.sourceid(sourceid);
    javaBuilder.reason(reason);
    javaBuilder.classes(classes);

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
    out.writeCard8(extension);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength() - 32);
    out.writeCard16(eventType);
    out.writeCard16(deviceid);
    out.writeCard32(time);
    short numClasses = (short) classes.size();
    out.writeCard16(numClasses);
    out.writeCard16(sourceid);
    out.writeCard8(reason);
    out.writePad(11);
    for(DeviceClass t : classes) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(classes);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DeviceChangedEventBuilder {
    public DeviceChangedEvent.DeviceChangedEventBuilder reason(ChangeReason reason) {
      this.reason = (byte) reason.getValue();
      return this;
    }

    public DeviceChangedEvent.DeviceChangedEventBuilder reason(byte reason) {
      this.reason = reason;
      return this;
    }

    public int getSize() {
      return 32 + XObject.sizeOf(classes);
    }
  }
}
