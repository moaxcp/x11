package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BarrierReleasePointerInfo implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private short deviceid;

  private int barrier;

  private int eventid;

  public static BarrierReleasePointerInfo readBarrierReleasePointerInfo(X11Input in) throws
      IOException {
    BarrierReleasePointerInfo.BarrierReleasePointerInfoBuilder javaBuilder = BarrierReleasePointerInfo.builder();
    short deviceid = in.readCard16();
    byte[] pad1 = in.readPad(2);
    int barrier = in.readCard32();
    int eventid = in.readCard32();
    javaBuilder.deviceid(deviceid);
    javaBuilder.barrier(barrier);
    javaBuilder.eventid(eventid);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(deviceid);
    out.writePad(2);
    out.writeCard32(barrier);
    out.writeCard32(eventid);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class BarrierReleasePointerInfoBuilder {
    public int getSize() {
      return 12;
    }
  }
}
