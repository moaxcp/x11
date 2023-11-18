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
public class KeyVModMap implements XStruct, XkbObject {
  private byte keycode;

  private short vmods;

  public static KeyVModMap readKeyVModMap(X11Input in) throws IOException {
    KeyVModMap.KeyVModMapBuilder javaBuilder = KeyVModMap.builder();
    byte keycode = in.readCard8();
    byte[] pad1 = in.readPad(1);
    short vmods = in.readCard16();
    javaBuilder.keycode(keycode);
    javaBuilder.vmods(vmods);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(keycode);
    out.writePad(1);
    out.writeCard16(vmods);
  }

  public boolean isVmodsEnabled(@NonNull VMod... maskEnums) {
    for(VMod m : maskEnums) {
      if(!m.isEnabled(vmods)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 4;
  }

  public static class KeyVModMapBuilder {
    public boolean isVmodsEnabled(@NonNull VMod... maskEnums) {
      for(VMod m : maskEnums) {
        if(!m.isEnabled(vmods)) {
          return false;
        }
      }
      return true;
    }

    public KeyVModMap.KeyVModMapBuilder vmodsEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        vmods((short) m.enableFor(vmods));
      }
      return this;
    }

    public KeyVModMap.KeyVModMapBuilder vmodsDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        vmods((short) m.disableFor(vmods));
      }
      return this;
    }

    public int getSize() {
      return 4;
    }
  }
}
