package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeviceAbsCalibCtl implements XStruct, XinputObject {
  private short controlId;

  private short len;

  private int minX;

  private int maxX;

  private int minY;

  private int maxY;

  private int flipX;

  private int flipY;

  private int rotation;

  private int buttonThreshold;

  public static DeviceAbsCalibCtl readDeviceAbsCalibCtl(X11Input in) throws IOException {
    DeviceAbsCalibCtl.DeviceAbsCalibCtlBuilder javaBuilder = DeviceAbsCalibCtl.builder();
    short controlId = in.readCard16();
    short len = in.readCard16();
    int minX = in.readInt32();
    int maxX = in.readInt32();
    int minY = in.readInt32();
    int maxY = in.readInt32();
    int flipX = in.readCard32();
    int flipY = in.readCard32();
    int rotation = in.readCard32();
    int buttonThreshold = in.readCard32();
    javaBuilder.controlId(controlId);
    javaBuilder.len(len);
    javaBuilder.minX(minX);
    javaBuilder.maxX(maxX);
    javaBuilder.minY(minY);
    javaBuilder.maxY(maxY);
    javaBuilder.flipX(flipX);
    javaBuilder.flipY(flipY);
    javaBuilder.rotation(rotation);
    javaBuilder.buttonThreshold(buttonThreshold);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(controlId);
    out.writeCard16(len);
    out.writeInt32(minX);
    out.writeInt32(maxX);
    out.writeInt32(minY);
    out.writeInt32(maxY);
    out.writeCard32(flipX);
    out.writeCard32(flipY);
    out.writeCard32(rotation);
    out.writeCard32(buttonThreshold);
  }

  @Override
  public int getSize() {
    return 36;
  }

  public static class DeviceAbsCalibCtlBuilder {
    public DeviceAbsCalibCtl.DeviceAbsCalibCtlBuilder controlId(DeviceControl controlId) {
      this.controlId = (short) controlId.getValue();
      return this;
    }

    public DeviceAbsCalibCtl.DeviceAbsCalibCtlBuilder controlId(short controlId) {
      this.controlId = controlId;
      return this;
    }

    public int getSize() {
      return 36;
    }
  }
}
