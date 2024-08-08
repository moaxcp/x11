package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class DeviceKeyStateNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte NUMBER = 13;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte deviceId;

  private short sequenceNumber;

  @NonNull
  private ByteList keys;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static DeviceKeyStateNotifyEvent readDeviceKeyStateNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    DeviceKeyStateNotifyEvent.DeviceKeyStateNotifyEventBuilder javaBuilder = DeviceKeyStateNotifyEvent.builder();
    byte deviceId = in.readByte();
    short sequenceNumber = in.readCard16();
    ByteList keys = in.readCard8(28);
    javaBuilder.deviceId(deviceId);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.keys(keys.toImmutable());

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
    out.writeByte(deviceId);
    out.writeCard16(sequenceNumber);
    out.writeCard8(keys);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 4 + 1 * keys.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DeviceKeyStateNotifyEventBuilder {
    public int getSize() {
      return 4 + 1 * keys.size();
    }
  }
}
