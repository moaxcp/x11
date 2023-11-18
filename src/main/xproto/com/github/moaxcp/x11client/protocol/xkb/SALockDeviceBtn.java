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
public class SALockDeviceBtn implements ActionUnion, XStruct, XkbObject {
  private byte type;

  private byte flags;

  private byte button;

  private byte device;

  public static SALockDeviceBtn readSALockDeviceBtn(X11Input in) throws IOException {
    SALockDeviceBtn.SALockDeviceBtnBuilder javaBuilder = SALockDeviceBtn.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte[] pad2 = in.readPad(1);
    byte button = in.readCard8();
    byte device = in.readCard8();
    byte[] pad5 = in.readPad(3);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.button(button);
    javaBuilder.device(device);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writePad(1);
    out.writeCard8(button);
    out.writeCard8(device);
    out.writePad(3);
  }

  public boolean isFlagsEnabled(@NonNull LockDeviceFlags... maskEnums) {
    for(LockDeviceFlags m : maskEnums) {
      if(!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class SALockDeviceBtnBuilder {
    public SALockDeviceBtn.SALockDeviceBtnBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SALockDeviceBtn.SALockDeviceBtnBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public boolean isFlagsEnabled(@NonNull LockDeviceFlags... maskEnums) {
      for(LockDeviceFlags m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public SALockDeviceBtn.SALockDeviceBtnBuilder flagsEnable(LockDeviceFlags... maskEnums) {
      for(LockDeviceFlags m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public SALockDeviceBtn.SALockDeviceBtnBuilder flagsDisable(LockDeviceFlags... maskEnums) {
      for(LockDeviceFlags m : maskEnums) {
        flags((byte) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
