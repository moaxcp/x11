package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ButtonInfo implements XStruct, XinputObject {
  private byte classId;

  private byte len;

  private short numButtons;

  public static ButtonInfo readButtonInfo(X11Input in) throws IOException {
    ButtonInfo.ButtonInfoBuilder javaBuilder = ButtonInfo.builder();
    byte classId = in.readCard8();
    byte len = in.readCard8();
    short numButtons = in.readCard16();
    javaBuilder.classId(classId);
    javaBuilder.len(len);
    javaBuilder.numButtons(numButtons);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(classId);
    out.writeCard8(len);
    out.writeCard16(numButtons);
  }

  @Override
  public int getSize() {
    return 4;
  }

  public static class ButtonInfoBuilder {
    public ButtonInfo.ButtonInfoBuilder classId(InputClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public ButtonInfo.ButtonInfoBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 4;
    }
  }
}
