package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class ButtonState implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private byte classId;

  private byte len;

  private byte numButtons;

  @NonNull
  private ByteList buttons;

  public static ButtonState readButtonState(X11Input in) throws IOException {
    ButtonState.ButtonStateBuilder javaBuilder = ButtonState.builder();
    byte classId = in.readCard8();
    byte len = in.readCard8();
    byte numButtons = in.readCard8();
    byte[] pad3 = in.readPad(1);
    ByteList buttons = in.readCard8(32);
    javaBuilder.classId(classId);
    javaBuilder.len(len);
    javaBuilder.numButtons(numButtons);
    javaBuilder.buttons(buttons.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(classId);
    out.writeCard8(len);
    out.writeCard8(numButtons);
    out.writePad(1);
    out.writeCard8(buttons);
  }

  @Override
  public int getSize() {
    return 4 + 1 * buttons.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ButtonStateBuilder {
    public ButtonState.ButtonStateBuilder classId(InputClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public ButtonState.ButtonStateBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public int getSize() {
      return 4 + 1 * buttons.size();
    }
  }
}
