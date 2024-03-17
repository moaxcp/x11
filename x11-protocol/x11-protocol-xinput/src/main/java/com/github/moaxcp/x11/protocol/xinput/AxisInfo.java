package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AxisInfo implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private int resolution;

  private int minimum;

  private int maximum;

  public static AxisInfo readAxisInfo(X11Input in) throws IOException {
    AxisInfo.AxisInfoBuilder javaBuilder = AxisInfo.builder();
    int resolution = in.readCard32();
    int minimum = in.readInt32();
    int maximum = in.readInt32();
    javaBuilder.resolution(resolution);
    javaBuilder.minimum(minimum);
    javaBuilder.maximum(maximum);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(resolution);
    out.writeInt32(minimum);
    out.writeInt32(maximum);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class AxisInfoBuilder {
    public int getSize() {
      return 12;
    }
  }
}
