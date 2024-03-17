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
public class ValuatorState implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private byte classId;

  private byte len;

  private byte mode;

  @NonNull
  private List<Integer> valuators;

  public static ValuatorState readValuatorState(X11Input in) throws IOException {
    ValuatorState.ValuatorStateBuilder javaBuilder = ValuatorState.builder();
    byte classId = in.readCard8();
    byte len = in.readCard8();
    byte numValuators = in.readCard8();
    byte mode = in.readCard8();
    List<Integer> valuators = in.readInt32(Byte.toUnsignedInt(numValuators));
    javaBuilder.classId(classId);
    javaBuilder.len(len);
    javaBuilder.mode(mode);
    javaBuilder.valuators(valuators);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(classId);
    out.writeCard8(len);
    byte numValuators = (byte) valuators.size();
    out.writeCard8(numValuators);
    out.writeCard8(mode);
    out.writeInt32(valuators);
  }

  public boolean isModeEnabled(@NonNull ValuatorStateModeMask... maskEnums) {
    for(ValuatorStateModeMask m : maskEnums) {
      if(!m.isEnabled(mode)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 4 + 4 * valuators.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ValuatorStateBuilder {
    public ValuatorState.ValuatorStateBuilder classId(InputClass classId) {
      this.classId = (byte) classId.getValue();
      return this;
    }

    public ValuatorState.ValuatorStateBuilder classId(byte classId) {
      this.classId = classId;
      return this;
    }

    public boolean isModeEnabled(@NonNull ValuatorStateModeMask... maskEnums) {
      for(ValuatorStateModeMask m : maskEnums) {
        if(!m.isEnabled(mode)) {
          return false;
        }
      }
      return true;
    }

    public ValuatorState.ValuatorStateBuilder modeEnable(ValuatorStateModeMask... maskEnums) {
      for(ValuatorStateModeMask m : maskEnums) {
        mode((byte) m.enableFor(mode));
      }
      return this;
    }

    public ValuatorState.ValuatorStateBuilder modeDisable(ValuatorStateModeMask... maskEnums) {
      for(ValuatorStateModeMask m : maskEnums) {
        mode((byte) m.disableFor(mode));
      }
      return this;
    }

    public int getSize() {
      return 4 + 4 * valuators.size();
    }
  }
}
