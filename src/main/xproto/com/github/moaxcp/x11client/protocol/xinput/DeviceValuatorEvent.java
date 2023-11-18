package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XEvent;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DeviceValuatorEvent implements XEvent, XinputObject {
  public static final byte NUMBER = 0;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte deviceId;

  private short sequenceNumber;

  private short deviceState;

  private byte numValuators;

  private byte firstValuator;

  @NonNull
  private List<Integer> valuators;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static DeviceValuatorEvent readDeviceValuatorEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    DeviceValuatorEvent.DeviceValuatorEventBuilder javaBuilder = DeviceValuatorEvent.builder();
    byte deviceId = in.readCard8();
    short sequenceNumber = in.readCard16();
    short deviceState = in.readCard16();
    byte numValuators = in.readCard8();
    byte firstValuator = in.readCard8();
    List<Integer> valuators = in.readInt32(6);
    javaBuilder.deviceId(deviceId);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.deviceState(deviceState);
    javaBuilder.numValuators(numValuators);
    javaBuilder.firstValuator(firstValuator);
    javaBuilder.valuators(valuators);

    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (0b10000000 & getResponseCode()) : getResponseCode());
    out.writeCard8(deviceId);
    out.writeCard16(sequenceNumber);
    out.writeCard16(deviceState);
    out.writeCard8(numValuators);
    out.writeCard8(firstValuator);
    out.writeInt32(valuators);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 4 * valuators.size();
  }

  public static class DeviceValuatorEventBuilder {
    public int getSize() {
      return 8 + 4 * valuators.size();
    }
  }
}
