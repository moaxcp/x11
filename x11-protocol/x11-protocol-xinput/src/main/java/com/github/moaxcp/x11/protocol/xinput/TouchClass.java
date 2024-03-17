package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TouchClass implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private short type;

  private short len;

  private short sourceid;

  private byte mode;

  private byte numTouches;

  public static TouchClass readTouchClass(X11Input in) throws IOException {
    TouchClass.TouchClassBuilder javaBuilder = TouchClass.builder();
    short type = in.readCard16();
    short len = in.readCard16();
    short sourceid = in.readCard16();
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class TouchClassBuilder {
    public TouchClass.TouchClassBuilder type(DeviceClassType type) {
      this.type = (short) type.getValue();
      return this;
    }

    public TouchClass.TouchClassBuilder type(short type) {
      this.type = type;
      return this;
    }

    public TouchClass.TouchClassBuilder mode(TouchMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public TouchClass.TouchClassBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
