package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeviceStateAbsCalib implements DeviceState, XinputObject {
  private short controlId;

  private short len;

  public static DeviceStateAbsCalib readDeviceStateAbsCalib(short controlId, short len, X11Input in)
      throws IOException {
    DeviceStateAbsCalib.DeviceStateAbsCalibBuilder javaBuilder = DeviceStateAbsCalib.builder();
    javaBuilder.controlId(controlId);
    javaBuilder.len(len);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(controlId);
    out.writeCard16(len);
  }

  @Override
  public int getSize() {
    return 4;
  }

  public static class DeviceStateAbsCalibBuilder {
    public DeviceStateAbsCalib.DeviceStateAbsCalibBuilder controlId(DeviceControl controlId) {
      this.controlId = (short) controlId.getValue();
      return this;
    }

    public DeviceStateAbsCalib.DeviceStateAbsCalibBuilder controlId(short controlId) {
      this.controlId = controlId;
      return this;
    }

    public int getSize() {
      return 4;
    }
  }
}
