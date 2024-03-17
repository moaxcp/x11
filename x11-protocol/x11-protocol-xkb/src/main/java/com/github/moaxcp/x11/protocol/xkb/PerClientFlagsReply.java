package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PerClientFlagsReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";

  private byte deviceID;

  private short sequenceNumber;

  private int supported;

  private int value;

  private int autoCtrls;

  private int autoCtrlsValues;

  public static PerClientFlagsReply readPerClientFlagsReply(byte deviceID, short sequenceNumber,
      X11Input in) throws IOException {
    PerClientFlagsReply.PerClientFlagsReplyBuilder javaBuilder = PerClientFlagsReply.builder();
    int length = in.readCard32();
    int supported = in.readCard32();
    int value = in.readCard32();
    int autoCtrls = in.readCard32();
    int autoCtrlsValues = in.readCard32();
    byte[] pad8 = in.readPad(8);
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.supported(supported);
    javaBuilder.value(value);
    javaBuilder.autoCtrls(autoCtrls);
    javaBuilder.autoCtrlsValues(autoCtrlsValues);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(deviceID);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(supported);
    out.writeCard32(value);
    out.writeCard32(autoCtrls);
    out.writeCard32(autoCtrlsValues);
    out.writePad(8);
  }

  public boolean isSupportedEnabled(@NonNull PerClientFlag... maskEnums) {
    for(PerClientFlag m : maskEnums) {
      if(!m.isEnabled(supported)) {
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
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PerClientFlagsReplyBuilder {
    public boolean isSupportedEnabled(@NonNull PerClientFlag... maskEnums) {
      for(PerClientFlag m : maskEnums) {
        if(!m.isEnabled(supported)) {
          return false;
        }
      }
      return true;
    }

    public PerClientFlagsReply.PerClientFlagsReplyBuilder supportedEnable(
        PerClientFlag... maskEnums) {
      for(PerClientFlag m : maskEnums) {
        supported((int) m.enableFor(supported));
      }
      return this;
    }

    public PerClientFlagsReply.PerClientFlagsReplyBuilder supportedDisable(
        PerClientFlag... maskEnums) {
      for(PerClientFlag m : maskEnums) {
        supported((int) m.disableFor(supported));
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

    public PerClientFlagsReply.PerClientFlagsReplyBuilder valueEnable(PerClientFlag... maskEnums) {
      for(PerClientFlag m : maskEnums) {
        value((int) m.enableFor(value));
      }
      return this;
    }

    public PerClientFlagsReply.PerClientFlagsReplyBuilder valueDisable(PerClientFlag... maskEnums) {
      for(PerClientFlag m : maskEnums) {
        value((int) m.disableFor(value));
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

    public PerClientFlagsReply.PerClientFlagsReplyBuilder autoCtrlsEnable(BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        autoCtrls((int) m.enableFor(autoCtrls));
      }
      return this;
    }

    public PerClientFlagsReply.PerClientFlagsReplyBuilder autoCtrlsDisable(BoolCtrl... maskEnums) {
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

    public PerClientFlagsReply.PerClientFlagsReplyBuilder autoCtrlsValuesEnable(
        BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        autoCtrlsValues((int) m.enableFor(autoCtrlsValues));
      }
      return this;
    }

    public PerClientFlagsReply.PerClientFlagsReplyBuilder autoCtrlsValuesDisable(
        BoolCtrl... maskEnums) {
      for(BoolCtrl m : maskEnums) {
        autoCtrlsValues((int) m.disableFor(autoCtrlsValues));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
