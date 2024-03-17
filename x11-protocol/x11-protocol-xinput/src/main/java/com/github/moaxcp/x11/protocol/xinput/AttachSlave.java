package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AttachSlave implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private short type;

  private short len;

  private short deviceid;

  private short master;

  public static AttachSlave readAttachSlave(X11Input in) throws IOException {
    AttachSlave.AttachSlaveBuilder javaBuilder = AttachSlave.builder();
    short type = in.readCard16();
    short len = in.readCard16();
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class AttachSlaveBuilder {
    public AttachSlave.AttachSlaveBuilder type(HierarchyChangeType type) {
      this.type = (short) type.getValue();
      return this;
    }

    public AttachSlave.AttachSlaveBuilder type(short type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
