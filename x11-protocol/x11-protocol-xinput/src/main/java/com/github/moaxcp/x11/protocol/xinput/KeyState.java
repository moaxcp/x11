package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class KeyState implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private byte classId;

  private byte len;

  private byte numKeys;

  @NonNull
  private List<Byte> keys;

  public static KeyState readKeyState(X11Input in) throws IOException {
    KeyState.KeyStateBuilder javaBuilder = KeyState.builder();
    byte classId = in.readCard8();
    byte len = in.readCard8();
    byte numKeys = in.readCard8();
    byte[] pad3 = in.readPad(1);
    List<Byte> keys = in.readCard8(32);
    javaBuilder.classId(classId);
    javaBuilder.len(len);
    javaBuilder.numKeys(numKeys);
    javaBuilder.keys(keys);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(classId);
    out.writeCard8(len);
    out.writeCard8(numKeys);
    out.writePad(1);
    out.writeCard8(keys);
  }

  @Override
  public int getSize() {
    return 4 + 1 * keys.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class KeyStateBuilder {
    public KeyState.KeyStateBuilder classId(InputClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public KeyState.KeyStateBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 4 + 1 * keys.size();
    }
  }
}
