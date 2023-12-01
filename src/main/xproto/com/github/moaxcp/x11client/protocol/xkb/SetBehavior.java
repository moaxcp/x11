package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetBehavior implements XStruct, XkbObject {
  private byte keycode;

  @NonNull
  private BehaviorUnion behavior;

  public static SetBehavior readSetBehavior(X11Input in) throws IOException {
    SetBehavior.SetBehaviorBuilder javaBuilder = SetBehavior.builder();
    byte keycode = in.readCard8();
    BehaviorUnion behavior = BehaviorUnion.readBehaviorUnion(in);
    byte[] pad2 = in.readPad(1);
    javaBuilder.keycode(keycode);
    javaBuilder.behavior(behavior);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(keycode);
    behavior.write(out);
    out.writePad(1);
  }

  @Override
  public int getSize() {
    return 23;
  }

  public static class SetBehaviorBuilder {
    public int getSize() {
      return 23;
    }
  }
}
