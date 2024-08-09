package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

@Value
@Builder
public class ButtonClass implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private short type;

  private short len;

  private short sourceid;

  @NonNull
  private ImmutableIntList state;

  @NonNull
  private ImmutableIntList labels;

  public static ButtonClass readButtonClass(X11Input in) throws IOException {
    ButtonClass.ButtonClassBuilder javaBuilder = ButtonClass.builder();
    short type = in.readCard16();
    short len = in.readCard16();
    short sourceid = in.readCard16();
    short numButtons = in.readCard16();
    ImmutableIntList state = in.readCard32((Short.toUnsignedInt(numButtons) + 31) / 32);
    ImmutableIntList labels = in.readCard32(Short.toUnsignedInt(numButtons));
    javaBuilder.type(type);
    javaBuilder.len(len);
    javaBuilder.sourceid(sourceid);
    javaBuilder.state(state);
    javaBuilder.labels(labels);
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

  public static class ButtonClassBuilder {
    public ButtonClass.ButtonClassBuilder type(DeviceClassType type) {
      this.type = (short) type.getValue();
      return this;
    }

    public ButtonClass.ButtonClassBuilder type(short type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8 + 4 * state.size() + 4 * labels.size();
    }
  }
}
