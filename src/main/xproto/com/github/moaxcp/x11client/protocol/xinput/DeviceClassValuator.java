package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DeviceClassValuator implements DeviceClass, XinputObject {
  private short type;

  private short len;

  private short sourceid;

  private short number;

  private int label;

  @NonNull
  private Fp3232 min;

  @NonNull
  private Fp3232 max;

  @NonNull
  private Fp3232 value;

  private int resolution;

  private byte mode;

  public static DeviceClassValuator readDeviceClassValuator(short type, short len, short sourceid,
      X11Input in) throws IOException {
    DeviceClassValuator.DeviceClassValuatorBuilder javaBuilder = DeviceClassValuator.builder();
    short number = in.readCard16();
    int label = in.readCard32();
    Fp3232 min = Fp3232.readFp3232(in);
    Fp3232 max = Fp3232.readFp3232(in);
    Fp3232 value = Fp3232.readFp3232(in);
    int resolution = in.readCard32();
    byte mode = in.readCard8();
    byte[] pad10 = in.readPad(3);
    javaBuilder.type(type);
    javaBuilder.len(len);
    javaBuilder.sourceid(sourceid);
    javaBuilder.number(number);
    javaBuilder.label(label);
    javaBuilder.min(min);
    javaBuilder.max(max);
    javaBuilder.value(value);
    javaBuilder.resolution(resolution);
    javaBuilder.mode(mode);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(type);
    out.writeCard16(len);
    out.writeCard16(sourceid);
    out.writeCard16(number);
    out.writeCard32(label);
    min.write(out);
    max.write(out);
    value.write(out);
    out.writeCard32(resolution);
    out.writeCard8(mode);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 44;
  }

  public static class DeviceClassValuatorBuilder {
    public DeviceClassValuator.DeviceClassValuatorBuilder type(DeviceClassType type) {
      this.type = (short) type.getValue();
      return this;
    }

    public DeviceClassValuator.DeviceClassValuatorBuilder type(short type) {
      this.type = type;
      return this;
    }

    public DeviceClassValuator.DeviceClassValuatorBuilder mode(ValuatorMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public DeviceClassValuator.DeviceClassValuatorBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 44;
    }
  }
}
