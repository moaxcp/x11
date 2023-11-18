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
public class SASetControls implements ActionUnion, XStruct, XkbObject {
  private byte type;

  private byte boolCtrlsHigh;

  private byte boolCtrlsLow;

  public static SASetControls readSASetControls(X11Input in) throws IOException {
    SASetControls.SASetControlsBuilder javaBuilder = SASetControls.builder();
    byte type = in.readCard8();
    byte[] pad1 = in.readPad(3);
    byte boolCtrlsHigh = in.readCard8();
    byte boolCtrlsLow = in.readCard8();
    byte[] pad4 = in.readPad(2);
    javaBuilder.type(type);
    javaBuilder.boolCtrlsHigh(boolCtrlsHigh);
    javaBuilder.boolCtrlsLow(boolCtrlsLow);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writePad(3);
    out.writeCard8(boolCtrlsHigh);
    out.writeCard8(boolCtrlsLow);
    out.writePad(2);
  }

  public boolean isBoolCtrlsHighEnabled(@NonNull BoolCtrlsHigh... maskEnums) {
    for(BoolCtrlsHigh m : maskEnums) {
      if(!m.isEnabled(boolCtrlsHigh)) {
        return false;
      }
    }
    return true;
  }

  public boolean isBoolCtrlsLowEnabled(@NonNull BoolCtrlsLow... maskEnums) {
    for(BoolCtrlsLow m : maskEnums) {
      if(!m.isEnabled(boolCtrlsLow)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class SASetControlsBuilder {
    public SASetControls.SASetControlsBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SASetControls.SASetControlsBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public boolean isBoolCtrlsHighEnabled(@NonNull BoolCtrlsHigh... maskEnums) {
      for(BoolCtrlsHigh m : maskEnums) {
        if(!m.isEnabled(boolCtrlsHigh)) {
          return false;
        }
      }
      return true;
    }

    public SASetControls.SASetControlsBuilder boolCtrlsHighEnable(BoolCtrlsHigh... maskEnums) {
      for(BoolCtrlsHigh m : maskEnums) {
        boolCtrlsHigh((byte) m.enableFor(boolCtrlsHigh));
      }
      return this;
    }

    public SASetControls.SASetControlsBuilder boolCtrlsHighDisable(BoolCtrlsHigh... maskEnums) {
      for(BoolCtrlsHigh m : maskEnums) {
        boolCtrlsHigh((byte) m.disableFor(boolCtrlsHigh));
      }
      return this;
    }

    public boolean isBoolCtrlsLowEnabled(@NonNull BoolCtrlsLow... maskEnums) {
      for(BoolCtrlsLow m : maskEnums) {
        if(!m.isEnabled(boolCtrlsLow)) {
          return false;
        }
      }
      return true;
    }

    public SASetControls.SASetControlsBuilder boolCtrlsLowEnable(BoolCtrlsLow... maskEnums) {
      for(BoolCtrlsLow m : maskEnums) {
        boolCtrlsLow((byte) m.enableFor(boolCtrlsLow));
      }
      return this;
    }

    public SASetControls.SASetControlsBuilder boolCtrlsLowDisable(BoolCtrlsLow... maskEnums) {
      for(BoolCtrlsLow m : maskEnums) {
        boolCtrlsLow((byte) m.disableFor(boolCtrlsLow));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
