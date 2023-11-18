package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SADeviceValuator implements ActionUnion, XStruct, XkbObject {
  private byte type;

  private byte device;

  private byte val1what;

  private byte val1index;

  private byte val1value;

  private byte val2what;

  private byte val2index;

  private byte val2value;

  public static SADeviceValuator readSADeviceValuator(X11Input in) throws IOException {
    SADeviceValuator.SADeviceValuatorBuilder javaBuilder = SADeviceValuator.builder();
    byte type = in.readCard8();
    byte device = in.readCard8();
    byte val1what = in.readCard8();
    byte val1index = in.readCard8();
    byte val1value = in.readCard8();
    byte val2what = in.readCard8();
    byte val2index = in.readCard8();
    byte val2value = in.readCard8();
    javaBuilder.type(type);
    javaBuilder.device(device);
    javaBuilder.val1what(val1what);
    javaBuilder.val1index(val1index);
    javaBuilder.val1value(val1value);
    javaBuilder.val2what(val2what);
    javaBuilder.val2index(val2index);
    javaBuilder.val2value(val2value);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(device);
    out.writeCard8(val1what);
    out.writeCard8(val1index);
    out.writeCard8(val1value);
    out.writeCard8(val2what);
    out.writeCard8(val2index);
    out.writeCard8(val2value);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class SADeviceValuatorBuilder {
    public SADeviceValuator.SADeviceValuatorBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SADeviceValuator.SADeviceValuatorBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public SADeviceValuator.SADeviceValuatorBuilder val1what(SAValWhat val1what) {
      this.val1what = (byte) val1what.getValue();
      return this;
    }

    public SADeviceValuator.SADeviceValuatorBuilder val1what(byte val1what) {
      this.val1what = val1what;
      return this;
    }

    public SADeviceValuator.SADeviceValuatorBuilder val2what(SAValWhat val2what) {
      this.val2what = (byte) val2what.getValue();
      return this;
    }

    public SADeviceValuator.SADeviceValuatorBuilder val2what(byte val2what) {
      this.val2what = val2what;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
