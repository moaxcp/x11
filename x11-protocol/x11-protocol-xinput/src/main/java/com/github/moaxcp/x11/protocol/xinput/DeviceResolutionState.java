package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class DeviceResolutionState implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private short controlId;

  private short len;

  @NonNull
  private IntList resolutionValues;

  @NonNull
  private IntList resolutionMin;

  @NonNull
  private IntList resolutionMax;

  public static DeviceResolutionState readDeviceResolutionState(X11Input in) throws IOException {
    DeviceResolutionState.DeviceResolutionStateBuilder javaBuilder = DeviceResolutionState.builder();
    short controlId = in.readCard16();
    short len = in.readCard16();
    int numValuators = in.readCard32();
    IntList resolutionValues = in.readCard32((int) (Integer.toUnsignedLong(numValuators)));
    IntList resolutionMin = in.readCard32((int) (Integer.toUnsignedLong(numValuators)));
    IntList resolutionMax = in.readCard32((int) (Integer.toUnsignedLong(numValuators)));
    javaBuilder.controlId(controlId);
    javaBuilder.len(len);
    javaBuilder.resolutionValues(resolutionValues.toImmutable());
    javaBuilder.resolutionMin(resolutionMin.toImmutable());
    javaBuilder.resolutionMax(resolutionMax.toImmutable());
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

  public String getPluginName() {
    return PLUGIN_NAME;
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
