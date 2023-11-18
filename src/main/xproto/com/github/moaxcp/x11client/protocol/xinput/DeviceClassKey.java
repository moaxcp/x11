package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DeviceClassKey implements DeviceClass, XinputObject {
  private short type;

  private short len;

  private short sourceid;

  @NonNull
  private List<Integer> keys;

  public static DeviceClassKey readDeviceClassKey(short type, short len, short sourceid,
      X11Input in) throws IOException {
    DeviceClassKey.DeviceClassKeyBuilder javaBuilder = DeviceClassKey.builder();
    short numKeys = in.readCard16();
    List<Integer> keys = in.readCard32(Short.toUnsignedInt(numKeys));
    javaBuilder.type(type);
    javaBuilder.len(len);
    javaBuilder.sourceid(sourceid);
    javaBuilder.keys(keys);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(type);
    out.writeCard16(len);
    out.writeCard16(sourceid);
    short numKeys = (short) keys.size();
    out.writeCard16(numKeys);
    out.writeCard32(keys);
  }

  @Override
  public int getSize() {
    return 8 + 4 * keys.size();
  }

  public static class DeviceClassKeyBuilder {
    public DeviceClassKey.DeviceClassKeyBuilder type(DeviceClassType type) {
      this.type = (short) type.getValue();
      return this;
    }

    public DeviceClassKey.DeviceClassKeyBuilder type(short type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8 + 4 * keys.size();
    }
  }
}
