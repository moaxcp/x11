package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class HierarchyInfo implements XStruct, XinputObject {
  private short deviceid;

  private short attachment;

  private byte type;

  private boolean enabled;

  private int flags;

  public static HierarchyInfo readHierarchyInfo(X11Input in) throws IOException {
    HierarchyInfo.HierarchyInfoBuilder javaBuilder = HierarchyInfo.builder();
    short deviceid = in.readCard16();
    short attachment = in.readCard16();
    byte type = in.readCard8();
    boolean enabled = in.readBool();
    byte[] pad4 = in.readPad(2);
    int flags = in.readCard32();
    javaBuilder.deviceid(deviceid);
    javaBuilder.attachment(attachment);
    javaBuilder.type(type);
    javaBuilder.enabled(enabled);
    javaBuilder.flags(flags);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(deviceid);
    out.writeCard16(attachment);
    out.writeCard8(type);
    out.writeBool(enabled);
    out.writePad(2);
    out.writeCard32(flags);
  }

  public boolean isFlagsEnabled(@NonNull HierarchyMask... maskEnums) {
    for(HierarchyMask m : maskEnums) {
      if(!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class HierarchyInfoBuilder {
    public HierarchyInfo.HierarchyInfoBuilder type(DeviceType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public HierarchyInfo.HierarchyInfoBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public boolean isFlagsEnabled(@NonNull HierarchyMask... maskEnums) {
      for(HierarchyMask m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public HierarchyInfo.HierarchyInfoBuilder flagsEnable(HierarchyMask... maskEnums) {
      for(HierarchyMask m : maskEnums) {
        flags((int) m.enableFor(flags));
      }
      return this;
    }

    public HierarchyInfo.HierarchyInfoBuilder flagsDisable(HierarchyMask... maskEnums) {
      for(HierarchyMask m : maskEnums) {
        flags((int) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
