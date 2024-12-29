package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class DeviceClassButton implements DeviceClass {
  public static final String PLUGIN_NAME = "xinput";

  private short type;

  private short len;

  private short sourceid;

  @NonNull
  private IntList state;

  @NonNull
  private IntList labels;

  public static DeviceClassButton readDeviceClassButton(short type, short len, short sourceid,
      X11Input in) throws IOException {
    DeviceClassButton.DeviceClassButtonBuilder javaBuilder = DeviceClassButton.builder();
    short numButtons = in.readCard16();
    IntList state = in.readCard32((Short.toUnsignedInt(numButtons) + 31) / 32);
    IntList labels = in.readCard32(Short.toUnsignedInt(numButtons));
    javaBuilder.type(type);
    javaBuilder.len(len);
    javaBuilder.sourceid(sourceid);
    javaBuilder.state(state.toImmutable());
    javaBuilder.labels(labels.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(type);
    out.writeCard16(len);
    out.writeCard16(sourceid);
    short numButtons = (short) labels.size();
    out.writeCard16(numButtons);
    out.writeCard32(state);
    out.writeCard32(labels);
  }

  @Override
  public int getSize() {
    return 8 + 4 * state.size() + 4 * labels.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DeviceClassButtonBuilder {
    public DeviceClassButton.DeviceClassButtonBuilder type(DeviceClassType type) {
      this.type = (short) type.getValue();
      return this;
    }

    public DeviceClassButton.DeviceClassButtonBuilder type(short type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8 + 4 * state.size() + 4 * labels.size();
    }
  }
}
