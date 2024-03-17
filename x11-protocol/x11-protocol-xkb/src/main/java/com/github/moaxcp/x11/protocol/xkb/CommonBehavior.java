package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CommonBehavior implements BehaviorUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte type;

  private byte data;

  public static CommonBehavior readCommonBehavior(X11Input in) throws IOException {
    CommonBehavior.CommonBehaviorBuilder javaBuilder = CommonBehavior.builder();
    byte type = in.readCard8();
    byte data = in.readCard8();
    javaBuilder.type(type);
    javaBuilder.data(data);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(data);
  }

  @Override
  public int getSize() {
    return 2;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CommonBehaviorBuilder {
    public int getSize() {
      return 2;
    }
  }
}
