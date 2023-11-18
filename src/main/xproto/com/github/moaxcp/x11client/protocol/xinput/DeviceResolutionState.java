package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DeviceResolutionState implements XStruct, XinputObject {
  private short controlId;

  private short len;

  @NonNull
  private List<Integer> resolutionValues;

  @NonNull
  private List<Integer> resolutionMin;

  @NonNull
  private List<Integer> resolutionMax;

  public static DeviceResolutionState readDeviceResolutionState(X11Input in) throws IOException {
    DeviceResolutionState.DeviceResolutionStateBuilder javaBuilder = DeviceResolutionState.builder();
    short controlId = in.readCard16();
    short len = in.readCard16();
    int numValuators = in.readCard32();
    List<Integer> resolutionValues = in.readCard32((int) (Integer.toUnsignedLong(numValuators)));
    List<Integer> resolutionMin = in.readCard32((int) (Integer.toUnsignedLong(numValuators)));
    List<Integer> resolutionMax = in.readCard32((int) (Integer.toUnsignedLong(numValuators)));
    javaBuilder.controlId(controlId);
    javaBuilder.len(len);
    javaBuilder.resolutionValues(resolutionValues);
    javaBuilder.resolutionMin(resolutionMin);
    javaBuilder.resolutionMax(resolutionMax);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(controlId);
    out.writeCard16(len);
    int numValuators = resolutionMax.size();
    out.writeCard32(numValuators);
    out.writeCard32(resolutionValues);
    out.writeCard32(resolutionMin);
    out.writeCard32(resolutionMax);
  }

  @Override
  public int getSize() {
    return 8 + 4 * resolutionValues.size() + 4 * resolutionMin.size() + 4 * resolutionMax.size();
  }

  public static class DeviceResolutionStateBuilder {
    public DeviceResolutionState.DeviceResolutionStateBuilder controlId(DeviceControl controlId) {
      this.controlId = (short) controlId.getValue();
      return this;
    }

    public DeviceResolutionState.DeviceResolutionStateBuilder controlId(short controlId) {
      this.controlId = controlId;
      return this;
    }

    public int getSize() {
      return 8 + 4 * resolutionValues.size() + 4 * resolutionMin.size() + 4 * resolutionMax.size();
    }
  }
}
