package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class InputStateKey implements InputState, XinputObject {
  private byte classId;

  private byte len;

  private byte numKeys;

  @NonNull
  private List<Byte> keys;

  public static InputStateKey readInputStateKey(byte classId, byte len, X11Input in) throws
      IOException {
    InputStateKey.InputStateKeyBuilder javaBuilder = InputStateKey.builder();
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
