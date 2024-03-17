package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeviceAbsAreaCtrl implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private short controlId;

  private short len;

  private int offsetX;

  private int offsetY;

  private int width;

  private int height;

  private int screen;

  private int following;

  public static DeviceAbsAreaCtrl readDeviceAbsAreaCtrl(X11Input in) throws IOException {
    DeviceAbsAreaCtrl.DeviceAbsAreaCtrlBuilder javaBuilder = DeviceAbsAreaCtrl.builder();
    short controlId = in.readCard16();
    short len = in.readCard16();
    int offsetX = in.readCard32();
    int offsetY = in.readCard32();
    int width = in.readInt32();
    int height = in.readInt32();
    int screen = in.readInt32();
    int following = in.readCard32();
    javaBuilder.controlId(controlId);
    javaBuilder.len(len);
    javaBuilder.offsetX(offsetX);
    javaBuilder.offsetY(offsetY);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.screen(screen);
    javaBuilder.following(following);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(controlId);
    out.writeCard16(len);
    out.writeCard32(offsetX);
    out.writeCard32(offsetY);
    out.writeInt32(width);
    out.writeInt32(height);
    out.writeInt32(screen);
    out.writeCard32(following);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DeviceAbsAreaCtrlBuilder {
    public DeviceAbsAreaCtrl.DeviceAbsAreaCtrlBuilder controlId(DeviceControl controlId) {
      this.controlId = (short) controlId.getValue();
      return this;
    }

    public DeviceAbsAreaCtrl.DeviceAbsAreaCtrlBuilder controlId(short controlId) {
      this.controlId = controlId;
      return this;
    }

    public int getSize() {
      return 28;
    }
  }
}
