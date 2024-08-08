package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class InputStateKey implements InputState {
  public static final String PLUGIN_NAME = "xinput";

  private byte classId;

  private byte len;

  private byte numKeys;

  @NonNull
  private ByteList keys;

  public static InputStateKey readInputStateKey(byte classId, byte len, X11Input in) throws
      IOException {
    InputStateKey.InputStateKeyBuilder javaBuilder = InputStateKey.builder();
    byte numKeys = in.readCard8();
    byte[] pad3 = in.readPad(1);
    ByteList keys = in.readCard8(32);
    javaBuilder.classId(classId);
    javaBuilder.len(len);
    javaBuilder.numKeys(numKeys);
    javaBuilder.keys(keys.toImmutable());
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

  public static class InputStateKeyBuilder {
    public InputStateKey.InputStateKeyBuilder classId(InputClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public InputStateKey.InputStateKeyBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 4 + 1 * keys.size();
    }
  }
}
