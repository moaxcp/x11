package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DetachSlave implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private short type;

  private short len;

  private short deviceid;

  public static DetachSlave readDetachSlave(X11Input in) throws IOException {
    DetachSlave.DetachSlaveBuilder javaBuilder = DetachSlave.builder();
    short type = in.readCard16();
    short len = in.readCard16();
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

  public static class DetachSlaveBuilder {
    public DetachSlave.DetachSlaveBuilder type(HierarchyChangeType type) {
      this.type = (short) type.getValue();
      return this;
    }

    public DetachSlave.DetachSlaveBuilder type(short type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
