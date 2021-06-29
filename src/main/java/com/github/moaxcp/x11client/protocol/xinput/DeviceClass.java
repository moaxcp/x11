package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XStruct;

import java.io.IOException;

public interface DeviceClass extends XStruct {
  static DeviceClass readDeviceClass(X11Input in) throws IOException {
    short type = in.readCard16();
    short len = in.readCard16();
    short sourceid = in.readCard16();
    if(DeviceClassType.getByCode(type) == DeviceClassType.KEY) {
      return DeviceClassKey.builder()
          .type(type)
          .len(len)
          .sourceid(sourceid)
          .build();
    }
    return null;
  }
}
