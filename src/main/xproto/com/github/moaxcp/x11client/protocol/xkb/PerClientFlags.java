package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PerClientFlags implements TwoWayRequest<PerClientFlagsReply>, XkbObject {
  public static final byte OPCODE = 21;

  private short deviceSpec;

  private int change;

  private int value;

  private int ctrlsToChange;

  private int autoCtrls;

  private int autoCtrlsValues;

  public XReplyFunction<PerClientFlagsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> PerClientFlagsReply.readPerClientFlagsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static PerClientFlags readPerClientFlags(X11Input in) throws IOException {
    PerClientFlags.PerClientFlagsBuilder javaBuilder = PerClientFlags.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte[] pad4 = in.readPad(2);
    int change = in.readCard32();
    int value = in.readCard32();
    int ctrlsToChange = in.readCard32();
    int autoCtrls = in.readCard32();
    int autoCtrlsValues = in.readCard32();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.change(change);
    javaBuilder.value(value);
    javaBuilder.ctrlsToChange(ctrlsToChange);
    javaBuilder.autoCtrls(autoCtrls);
    javaBuilder.autoCtrlsValues(autoCtrlsValues);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writePad(2);
    out.writeCard32(change);
    out.writeCard32(value);
    out.writeCard32(ctrlsToChange);
    out.writeCard32(autoCtrls);
    out.writeCard32(autoCtrlsValues);
  }

  public boolean isChangeEnabled(@NonNull PerClientFlag... maskEnums) {
    for(PerClientFlag m : maskEnums) {
      if(!m.isEnabled(change)) {
        return false;
      }
    }
    return true;
  }

  public boolean isValueEnabled(@NonNull PerClientFlag... maskEnums) {
    for(PerClientFlag m : maskEnums) {
      if(!m.isEnabled(value)) {
        return false;
      }
    }
    return true;
  }

  public boolean isCtrlsToChangeEnabled(@NonNull BoolCtrl... maskEnums) {
    for(BoolCtrl m : maskEnums) {
      if(!m.isEnabled(ctrlsToChange)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAutoCtrlsEnabled(@NonNull BoolCtrl... maskEnums) {
    for(BoolCtrl m : maskEnums) {
      if(!m.isEnabled(autoCtrls)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAutoCtrlsValuesEnabled(@NonNull BoolCtrl... maskEnums) {
    for(BoolCtrl m : maskEnums) {
      if(!m.isEnabled(autoCtrlsValues)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 28;
  }

  public static class PerClientFlagsBuilder {
    public boolean isChangeEnabled(@NonNull PerClientFlag... maskEnums) {
      for(PerClientFlag m : maskEnums) {
        if(!m.isEnabled(change)) {
          return false;
        }
      }
      return true;
    }

    public PerClientFlags.PerClientFlagsBuilder changeEnable(PerClientFlag... maskEnums) {
      for(PerClientFlag m : maskEnums) {
        change((int) m.enableFor(change));
      }
      return this;
    }

    public PerClientFlags.PerClientFlagsBuilder changeDisable(PerClientFlag... maskEnums) {
      for(PerClientFlag m : maskEnums) {
        change((int) m.disableFor(change));
      }
      return this;
    }

    public boolean isValueEnabled(@NonNull PerClientFlag... maskEnums) {
      for(PerClientFlag m : maskEnums) {
        if(!m.isEnabled(value)) {
          return false;
        }
      }
      return true;
    }

    public PerClientFlags.PerClientFlagsBuilder valueEnable(PerClientFlag... maskEnums) {
      for(PerClientFlag m : maskEnums) {
        value((int) m.enableFor(value));
      }
      return this;
    }

    public PerClientFlags.PerClientFlagsBuilder valueDisable(PerClientFlag... maskEnums) {
      for(PerClientFlag m : maskEnums) {
        value((int) m.disableFor(value));
      }
      return this;
    }

    public boolean isCtrlsToChangeEnabled(@NonNull BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        if(!m.isEnabled(ctrlsToChange)) {
          return false;
        }
      }
      return true;
    }

    public PerClientFlags.PerClientFlagsBuilder ctrlsToChangeEnable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        ctrlsToChange((int) m.enableFor(ctrlsToChange));
      }
      return this;
    }

    public PerClientFlags.PerClientFlagsBuilder ctrlsToChangeDisable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        ctrlsToChange((int) m.disableFor(ctrlsToChange));
      }
      return this;
    }

    public boolean isAutoCtrlsEnabled(@NonNull BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        if(!m.isEnabled(autoCtrls)) {
          return false;
        }
      }
      return true;
    }

    public PerClientFlags.PerClientFlagsBuilder autoCtrlsEnable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        autoCtrls((int) m.enableFor(autoCtrls));
      }
      return this;
    }

    public PerClientFlags.PerClientFlagsBuilder autoCtrlsDisable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        autoCtrls((int) m.disableFor(autoCtrls));
      }
      return this;
    }

    public boolean isAutoCtrlsValuesEnabled(@NonNull BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        if(!m.isEnabled(autoCtrlsValues)) {
          return false;
        }
      }
      return true;
    }

    public PerClientFlags.PerClientFlagsBuilder autoCtrlsValuesEnable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        autoCtrlsValues((int) m.enableFor(autoCtrlsValues));
      }
      return this;
    }

    public PerClientFlags.PerClientFlagsBuilder autoCtrlsValuesDisable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        autoCtrlsValues((int) m.disableFor(autoCtrlsValues));
      }
      return this;
    }

    public int getSize() {
      return 28;
    }
  }
}
