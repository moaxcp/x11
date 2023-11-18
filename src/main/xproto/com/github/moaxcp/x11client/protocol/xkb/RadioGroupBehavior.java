package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RadioGroupBehavior implements BehaviorUnion, XStruct, XkbObject {
  private byte type;

  private byte group;

  public static RadioGroupBehavior readRadioGroupBehavior(X11Input in) throws IOException {
    RadioGroupBehavior.RadioGroupBehaviorBuilder javaBuilder = RadioGroupBehavior.builder();
    byte type = in.readCard8();
    byte group = in.readCard8();
    javaBuilder.type(type);
    javaBuilder.group(group);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(group);
  }

  @Override
  public int getSize() {
    return 2;
  }

  public static class RadioGroupBehaviorBuilder {
    public int getSize() {
      return 2;
    }
  }
}
