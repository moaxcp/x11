package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HierarchyChangeAttachSlave implements HierarchyChange, XinputObject {
  private short type;

  private short len;

  private short deviceid;

  private short master;

  public static HierarchyChangeAttachSlave readHierarchyChangeAttachSlave(short type, short len,
      X11Input in) throws IOException {
    HierarchyChangeAttachSlave.HierarchyChangeAttachSlaveBuilder javaBuilder = HierarchyChangeAttachSlave.builder();
    short deviceid = in.readCard16();
    short master = in.readCard16();
    javaBuilder.type(type);
    javaBuilder.len(len);
    javaBuilder.deviceid(deviceid);
    javaBuilder.master(master);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(type);
    out.writeCard16(len);
    out.writeCard16(deviceid);
    out.writeCard16(master);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class HierarchyChangeAttachSlaveBuilder {
    public HierarchyChangeAttachSlave.HierarchyChangeAttachSlaveBuilder type(
        HierarchyChangeType type) {
      this.type = (short) type.getValue();
      return this;
    }

    public HierarchyChangeAttachSlave.HierarchyChangeAttachSlaveBuilder type(short type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
