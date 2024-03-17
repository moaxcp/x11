package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DefaultBehavior implements BehaviorUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte type;

  public static DefaultBehavior readDefaultBehavior(X11Input in) throws IOException {
    DefaultBehavior.DefaultBehaviorBuilder javaBuilder = DefaultBehavior.builder();
    byte type = in.readCard8();
    byte[] pad1 = in.readPad(1);
    javaBuilder.type(type);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writePad(1);
  }

  @Override
  public int getSize() {
    return 2;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DefaultBehaviorBuilder {
    public int getSize() {
      return 2;
    }
  }
}
