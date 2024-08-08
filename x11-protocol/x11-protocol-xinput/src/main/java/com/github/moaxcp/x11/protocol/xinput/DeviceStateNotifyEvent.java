package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class DeviceStateNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte NUMBER = 10;

  private byte firstEventOffset;

  private boolean sentEvent;

  private byte deviceId;

  private short sequenceNumber;

  private int time;

  private byte numKeys;

  private byte numButtons;

  private byte numValuators;

  private byte classesReported;

  @NonNull
  private ByteList buttons;

  @NonNull
  private ByteList keys;

  @NonNull
  private IntList valuators;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static DeviceStateNotifyEvent readDeviceStateNotifyEvent(byte firstEventOffset,
      boolean sentEvent, X11Input in) throws IOException {
    DeviceStateNotifyEvent.DeviceStateNotifyEventBuilder javaBuilder = DeviceStateNotifyEvent.builder();
    byte deviceId = in.readByte();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte numKeys = in.readCard8();
    byte numButtons = in.readCard8();
    byte numValuators = in.readCard8();
    byte classesReported = in.readCard8();
    ByteList buttons = in.readCard8(4);
    ByteList keys = in.readCard8(4);
    IntList valuators = in.readCard32(3);
    javaBuilder.deviceId(deviceId);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.numKeys(numKeys);
    javaBuilder.numButtons(numButtons);
    javaBuilder.numValuators(numValuators);
    javaBuilder.classesReported(classesReported);
    javaBuilder.buttons(buttons.toImmutable());
    javaBuilder.keys(keys.toImmutable());
    javaBuilder.valuators(valuators.toImmutable());

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
    out.writeCard32(time);
    out.writeCard8(numKeys);
    out.writeCard8(numButtons);
    out.writeCard8(numValuators);
    out.writeCard8(classesReported);
    out.writeCard8(buttons);
    out.writeCard8(keys);
    out.writeCard32(valuators);
    out.writePadAlign(getSize());
  }

  public boolean isClassesReportedEnabled(@NonNull ClassesReportedMask... maskEnums) {
    for(ClassesReportedMask m : maskEnums) {
      if(!m.isEnabled(classesReported)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 12 + 1 * buttons.size() + 1 * keys.size() + 4 * valuators.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DeviceStateNotifyEventBuilder {
    public boolean isClassesReportedEnabled(@NonNull ClassesReportedMask... maskEnums) {
      for(ClassesReportedMask m : maskEnums) {
        if(!m.isEnabled(classesReported)) {
          return false;
        }
      }
      return true;
    }

    public DeviceStateNotifyEvent.DeviceStateNotifyEventBuilder classesReportedEnable(
        ClassesReportedMask... maskEnums) {
      for(ClassesReportedMask m : maskEnums) {
        classesReported((byte) m.enableFor(classesReported));
      }
      return this;
    }

    public DeviceStateNotifyEvent.DeviceStateNotifyEventBuilder classesReportedDisable(
        ClassesReportedMask... maskEnums) {
      for(ClassesReportedMask m : maskEnums) {
        classesReported((byte) m.disableFor(classesReported));
      }
      return this;
    }

    public int getSize() {
      return 12 + 1 * buttons.size() + 1 * keys.size() + 4 * valuators.size();
    }
  }
}
