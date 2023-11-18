package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeviceAbsAreaState implements XStruct, XinputObject {
  private short controlId;

  private short len;

  private int offsetX;

  private int offsetY;

  private int width;

  private int height;

  private int screen;

  private int following;

  public static DeviceAbsAreaState readDeviceAbsAreaState(X11Input in) throws IOException {
    DeviceAbsAreaState.DeviceAbsAreaStateBuilder javaBuilder = DeviceAbsAreaState.builder();
    short controlId = in.readCard16();
    short len = in.readCard16();
    int offsetX = in.readCard32();
    int offsetY = in.readCard32();
    int width = in.readCard32();
    int height = in.readCard32();
    int screen = in.readCard32();
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
    out.writeCard32(width);
    out.writeCard32(height);
    out.writeCard32(screen);
    out.writeCard32(following);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public static class DeviceAbsAreaStateBuilder {
    public DeviceAbsAreaState.DeviceAbsAreaStateBuilder controlId(DeviceControl controlId) {
      this.controlId = (short) controlId.getValue();
      return this;
    }

    public DeviceAbsAreaState.DeviceAbsAreaStateBuilder controlId(short controlId) {
      this.controlId = controlId;
      return this;
    }

    public int getSize() {
      return 28;
    }
  }
}
