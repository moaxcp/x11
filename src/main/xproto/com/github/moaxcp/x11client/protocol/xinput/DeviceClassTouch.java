package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeviceClassTouch implements DeviceClass, XinputObject {
  private short type;

  private short len;

  private short sourceid;

  private byte mode;

  private byte numTouches;

  public static DeviceClassTouch readDeviceClassTouch(short type, short len, short sourceid,
      X11Input in) throws IOException {
    DeviceClassTouch.DeviceClassTouchBuilder javaBuilder = DeviceClassTouch.builder();
    byte mode = in.readCard8();
    byte numTouches = in.readCard8();
    javaBuilder.type(type);
    javaBuilder.len(len);
    javaBuilder.sourceid(sourceid);
    javaBuilder.mode(mode);
    javaBuilder.numTouches(numTouches);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(type);
    out.writeCard16(len);
    out.writeCard16(sourceid);
    out.writeCard8(mode);
    out.writeCard8(numTouches);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class DeviceClassTouchBuilder {
    public DeviceClassTouch.DeviceClassTouchBuilder type(DeviceClassType type) {
      this.type = (short) type.getValue();
      return this;
    }

    public DeviceClassTouch.DeviceClassTouchBuilder type(short type) {
      this.type = type;
      return this;
    }

    public DeviceClassTouch.DeviceClassTouchBuilder mode(TouchMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public DeviceClassTouch.DeviceClassTouchBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
