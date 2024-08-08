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
public class DeviceButtonStateNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte NUMBER = 14;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte deviceId;

  private short sequenceNumber;

  @NonNull
  private ByteList buttons;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static DeviceButtonStateNotifyEvent readDeviceButtonStateNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    DeviceButtonStateNotifyEvent.DeviceButtonStateNotifyEventBuilder javaBuilder = DeviceButtonStateNotifyEvent.builder();
    byte deviceId = in.readByte();
    short sequenceNumber = in.readCard16();
    ByteList buttons = in.readCard8(28);
    javaBuilder.deviceId(deviceId);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.buttons(buttons.toImmutable());

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
    out.writeCard8(buttons);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 4 + 1 * buttons.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DeviceButtonStateNotifyEventBuilder {
    public int getSize() {
      return 4 + 1 * buttons.size();
    }
  }
}
