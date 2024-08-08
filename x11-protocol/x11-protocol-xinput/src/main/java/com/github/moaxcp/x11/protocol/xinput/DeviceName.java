package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class DeviceName implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  @NonNull
  private ByteList string;

  public static DeviceName readDeviceName(X11Input in) throws IOException {
    DeviceName.DeviceNameBuilder javaBuilder = DeviceName.builder();
    byte len = in.readCard8();
    ByteList string = in.readChar(Byte.toUnsignedInt(len));
    javaBuilder.string(string.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    byte len = (byte) string.size();
    out.writeCard8(len);
    out.writeChar(string);
  }

  @Override
  public int getSize() {
    return 1 + 1 * string.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DeviceNameBuilder {
    public int getSize() {
      return 1 + 1 * string.size();
    }
  }
}
