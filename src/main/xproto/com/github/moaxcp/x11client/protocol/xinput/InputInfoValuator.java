package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class InputInfoValuator implements InputInfo, XinputObject {
  private byte classId;

  private byte len;

  private byte mode;

  private int motionSize;

  @NonNull
  private List<AxisInfo> axes;

  public static InputInfoValuator readInputInfoValuator(byte classId, byte len, X11Input in) throws
      IOException {
    InputInfoValuator.InputInfoValuatorBuilder javaBuilder = InputInfoValuator.builder();
    byte axesLen = in.readCard8();
    byte mode = in.readCard8();
    int motionSize = in.readCard32();
    List<AxisInfo> axes = new ArrayList<>(Byte.toUnsignedInt(axesLen));
    for(int i = 0; i < Byte.toUnsignedInt(axesLen); i++) {
      axes.add(AxisInfo.readAxisInfo(in));
    }
    javaBuilder.classId(classId);
    javaBuilder.len(len);
    javaBuilder.mode(mode);
    javaBuilder.motionSize(motionSize);
    javaBuilder.axes(axes);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(classId);
    out.writeCard8(len);
    byte axesLen = (byte) axes.size();
    out.writeCard8(axesLen);
    out.writeCard8(mode);
    out.writeCard32(motionSize);
    for(AxisInfo t : axes) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(axes);
  }

  public static class InputInfoValuatorBuilder {
    public InputInfoValuator.InputInfoValuatorBuilder classId(InputClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public InputInfoValuator.InputInfoValuatorBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public InputInfoValuator.InputInfoValuatorBuilder mode(ValuatorMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public InputInfoValuator.InputInfoValuatorBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 8 + XObject.sizeOf(axes);
    }
  }
}
