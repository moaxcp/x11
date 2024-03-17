package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InputInfoButton implements InputInfo {
  public static final String PLUGIN_NAME = "xinput";

  private byte classId;

  private byte len;

  private short numButtons;

  public static InputInfoButton readInputInfoButton(byte classId, byte len, X11Input in) throws
      IOException {
    InputInfoButton.InputInfoButtonBuilder javaBuilder = InputInfoButton.builder();
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class InputInfoButtonBuilder {
    public InputInfoButton.InputInfoButtonBuilder classId(InputClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public InputInfoButton.InputInfoButtonBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 4;
    }
  }
}
