package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeviceCoreState implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private short controlId;

  private short len;

  private byte status;

  private byte iscore;

  public static DeviceCoreState readDeviceCoreState(X11Input in) throws IOException {
    DeviceCoreState.DeviceCoreStateBuilder javaBuilder = DeviceCoreState.builder();
    short controlId = in.readCard16();
    short len = in.readCard16();
    byte status = in.readCard8();
    byte iscore = in.readCard8();
    byte[] pad4 = in.readPad(2);
    javaBuilder.controlId(controlId);
    javaBuilder.len(len);
    javaBuilder.status(status);
    javaBuilder.iscore(iscore);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(controlId);
    out.writeCard16(len);
    out.writeCard8(status);
    out.writeCard8(iscore);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DeviceCoreStateBuilder {
    public DeviceCoreState.DeviceCoreStateBuilder controlId(DeviceControl controlId) {
      this.controlId = (short) controlId.getValue();
      return this;
    }

    public DeviceCoreState.DeviceCoreStateBuilder controlId(short controlId) {
      this.controlId = controlId;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
