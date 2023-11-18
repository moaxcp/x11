package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeviceEnableCtrl implements XStruct, XinputObject {
  private short controlId;

  private short len;

  private byte enable;

  public static DeviceEnableCtrl readDeviceEnableCtrl(X11Input in) throws IOException {
    DeviceEnableCtrl.DeviceEnableCtrlBuilder javaBuilder = DeviceEnableCtrl.builder();
    short controlId = in.readCard16();
    short len = in.readCard16();
    byte enable = in.readCard8();
    byte[] pad3 = in.readPad(3);
    javaBuilder.controlId(controlId);
    javaBuilder.len(len);
    javaBuilder.enable(enable);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(controlId);
    out.writeCard16(len);
    out.writeCard8(enable);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class DeviceEnableCtrlBuilder {
    public DeviceEnableCtrl.DeviceEnableCtrlBuilder controlId(DeviceControl controlId) {
      this.controlId = (short) controlId.getValue();
      return this;
    }

    public DeviceEnableCtrl.DeviceEnableCtrlBuilder controlId(short controlId) {
      this.controlId = controlId;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
