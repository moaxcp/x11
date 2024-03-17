package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HierarchyChangeDetachSlave implements HierarchyChange {
  public static final String PLUGIN_NAME = "xinput";

  private short type;

  private short len;

  private short deviceid;

  public static HierarchyChangeDetachSlave readHierarchyChangeDetachSlave(short type, short len,
      X11Input in) throws IOException {
    HierarchyChangeDetachSlave.HierarchyChangeDetachSlaveBuilder javaBuilder = HierarchyChangeDetachSlave.builder();
    short deviceid = in.readCard16();
    byte[] pad3 = in.readPad(2);
    javaBuilder.type(type);
    javaBuilder.len(len);
    javaBuilder.deviceid(deviceid);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(type);
    out.writeCard16(len);
    out.writeCard16(deviceid);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class HierarchyChangeDetachSlaveBuilder {
    public HierarchyChangeDetachSlave.HierarchyChangeDetachSlaveBuilder type(
        HierarchyChangeType type) {
      this.type = (short) type.getValue();
      return this;
    }

    public HierarchyChangeDetachSlave.HierarchyChangeDetachSlaveBuilder type(short type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
