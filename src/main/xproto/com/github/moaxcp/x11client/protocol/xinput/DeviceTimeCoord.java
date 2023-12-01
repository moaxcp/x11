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
public class DeviceTimeCoord implements XStruct, XinputObject {
  private int time;

  @NonNull
  private List<Integer> axisvalues;

  public static DeviceTimeCoord readDeviceTimeCoord(byte numAxes, X11Input in) throws IOException {
    DeviceTimeCoord.DeviceTimeCoordBuilder javaBuilder = DeviceTimeCoord.builder();
    int time = in.readCard32();
    List<Integer> axisvalues = in.readInt32(numAxes);
    javaBuilder.time(time);
    javaBuilder.axisvalues(axisvalues);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(time);
    out.writeInt32(axisvalues);
  }

  @Override
  public int getSize() {
    return 4 + 4 * axisvalues.size();
  }

  public static class DeviceTimeCoordBuilder {
    public int getSize() {
      return 4 + 4 * axisvalues.size();
    }
  }
}
