package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class GetDeviceMotionEventsReply implements XReply {
  public static final String PLUGIN_NAME = "xinput";

  private byte xiReplyType;

  private short sequenceNumber;

  private byte numAxes;

  private byte deviceMode;

  @NonNull
  private ImmutableList<DeviceTimeCoord> events;

  public static GetDeviceMotionEventsReply readGetDeviceMotionEventsReply(byte xiReplyType,
      short sequenceNumber, X11Input in) throws IOException {
    GetDeviceMotionEventsReply.GetDeviceMotionEventsReplyBuilder javaBuilder = GetDeviceMotionEventsReply.builder();
    int length = in.readCard32();
    int numEvents = in.readCard32();
    byte numAxes = in.readCard8();
    byte deviceMode = in.readCard8();
    byte[] pad7 = in.readPad(18);
    MutableList<DeviceTimeCoord> events = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numEvents)));
    for(int i = 0; i < Integer.toUnsignedLong(numEvents); i++) {
      events.add(DeviceTimeCoord.readDeviceTimeCoord(numAxes, in));
    }
    javaBuilder.xiReplyType(xiReplyType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.numAxes(numAxes);
    javaBuilder.deviceMode(deviceMode);
    javaBuilder.events(events.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(xiReplyType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    int numEvents = events.size();
    out.writeCard32(numEvents);
    out.writeCard8(numAxes);
    out.writeCard8(deviceMode);
    out.writePad(18);
    for(DeviceTimeCoord t : events) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(events);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDeviceMotionEventsReplyBuilder {
    public GetDeviceMotionEventsReply.GetDeviceMotionEventsReplyBuilder deviceMode(
        ValuatorMode deviceMode) {
      this.deviceMode = (byte) deviceMode.getValue();
      return this;
    }

    public GetDeviceMotionEventsReply.GetDeviceMotionEventsReplyBuilder deviceMode(
        byte deviceMode) {
      this.deviceMode = deviceMode;
      return this;
    }

    public int getSize() {
      return 32 + XObject.sizeOf(events);
    }
  }
}
