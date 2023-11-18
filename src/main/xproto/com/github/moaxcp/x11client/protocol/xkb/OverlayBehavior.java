package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OverlayBehavior implements BehaviorUnion, XStruct, XkbObject {
  private byte type;

  private byte key;

  public static OverlayBehavior readOverlayBehavior(X11Input in) throws IOException {
    OverlayBehavior.OverlayBehaviorBuilder javaBuilder = OverlayBehavior.builder();
    byte type = in.readCard8();
    byte key = in.readCard8();
    javaBuilder.type(type);
    javaBuilder.key(key);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(key);
  }

  @Override
  public int getSize() {
    return 2;
  }

  public static class OverlayBehaviorBuilder {
    public int getSize() {
      return 2;
    }
  }
}
