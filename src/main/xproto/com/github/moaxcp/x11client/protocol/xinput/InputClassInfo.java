package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InputClassInfo implements XStruct, XinputObject {
  private byte classId;

  private byte eventTypeBase;

  public static InputClassInfo readInputClassInfo(X11Input in) throws IOException {
    InputClassInfo.InputClassInfoBuilder javaBuilder = InputClassInfo.builder();
    byte classId = in.readCard8();
    byte eventTypeBase = in.readCard8();
    javaBuilder.classId(classId);
    javaBuilder.eventTypeBase(eventTypeBase);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(classId);
    out.writeCard8(eventTypeBase);
  }

  @Override
  public int getSize() {
    return 2;
  }

  public static class InputClassInfoBuilder {
    public InputClassInfo.InputClassInfoBuilder classId(InputClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public InputClassInfo.InputClassInfoBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 2;
    }
  }
}
