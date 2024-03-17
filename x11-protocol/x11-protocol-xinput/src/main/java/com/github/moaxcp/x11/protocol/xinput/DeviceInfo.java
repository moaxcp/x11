package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeviceInfo implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private int deviceType;

  private byte deviceId;

  private byte numClassInfo;

  private byte deviceUse;

  public static DeviceInfo readDeviceInfo(X11Input in) throws IOException {
    DeviceInfo.DeviceInfoBuilder javaBuilder = DeviceInfo.builder();
    int deviceType = in.readCard32();
    byte deviceId = in.readCard8();
    byte numClassInfo = in.readCard8();
    byte deviceUse = in.readCard8();
    byte[] pad4 = in.readPad(1);
    javaBuilder.deviceType(deviceType);
    javaBuilder.deviceId(deviceId);
    javaBuilder.numClassInfo(numClassInfo);
    javaBuilder.deviceUse(deviceUse);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(deviceType);
    out.writeCard8(deviceId);
    out.writeCard8(numClassInfo);
    out.writeCard8(deviceUse);
    out.writePad(1);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DeviceInfoBuilder {
    public DeviceInfo.DeviceInfoBuilder deviceUse(DeviceUse deviceUse) {
      this.deviceUse = (byte) deviceUse.getValue();
      return this;
    }

    public DeviceInfo.DeviceInfoBuilder deviceUse(byte deviceUse) {
      this.deviceUse = deviceUse;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
