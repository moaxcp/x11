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
public class SetExplicit implements XStruct, XkbObject {
  private byte keycode;

  private byte explicit;

  public static SetExplicit readSetExplicit(X11Input in) throws IOException {
    SetExplicit.SetExplicitBuilder javaBuilder = SetExplicit.builder();
    byte keycode = in.readCard8();
    byte explicit = in.readCard8();
    javaBuilder.keycode(keycode);
    javaBuilder.explicit(explicit);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(keycode);
    out.writeCard8(explicit);
  }

  public boolean isExplicitEnabled(@NonNull Explicit... maskEnums) {
    for(Explicit m : maskEnums) {
      if(!m.isEnabled(explicit)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 2;
  }

  public static class SetExplicitBuilder {
    public boolean isExplicitEnabled(@NonNull Explicit... maskEnums) {
      for(Explicit m : maskEnums) {
        if(!m.isEnabled(explicit)) {
          return false;
        }
      }
      return true;
    }

    public SetExplicit.SetExplicitBuilder explicitEnable(Explicit... maskEnums) {
      for(Explicit m : maskEnums) {
        explicit((byte) m.enableFor(explicit));
      }
      return this;
    }

    public SetExplicit.SetExplicitBuilder explicitDisable(Explicit... maskEnums) {
      for(Explicit m : maskEnums) {
        explicit((byte) m.disableFor(explicit));
      }
      return this;
    }

    public int getSize() {
      return 2;
    }
  }
}
