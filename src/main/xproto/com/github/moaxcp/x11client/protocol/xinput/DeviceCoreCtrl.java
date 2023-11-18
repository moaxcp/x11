package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeviceCoreCtrl implements XStruct, XinputObject {
  private short controlId;

  private short len;

  private byte status;

  public static DeviceCoreCtrl readDeviceCoreCtrl(X11Input in) throws IOException {
    DeviceCoreCtrl.DeviceCoreCtrlBuilder javaBuilder = DeviceCoreCtrl.builder();
    short controlId = in.readCard16();
    short len = in.readCard16();
    byte status = in.readCard8();
    byte[] pad3 = in.readPad(3);
    javaBuilder.controlId(controlId);
    javaBuilder.len(len);
    javaBuilder.status(status);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(controlId);
    out.writeCard16(len);
    out.writeCard8(status);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class DeviceCoreCtrlBuilder {
    public DeviceCoreCtrl.DeviceCoreCtrlBuilder controlId(DeviceControl controlId) {
      this.controlId = (short) controlId.getValue();
      return this;
    }

    public DeviceCoreCtrl.DeviceCoreCtrlBuilder controlId(short controlId) {
      this.controlId = controlId;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
