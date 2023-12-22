package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.XStruct;

import java.io.IOException;

public interface DeviceClass extends XStruct {
  static DeviceClass readDeviceClass(X11Input in) throws IOException {
    short type = in.readCard16();
    short len = in.readCard16();
    short sourceid = in.readCard16();
    DeviceClassType deviceClassType = DeviceClassType.getByCode(type);
    if(deviceClassType == DeviceClassType.KEY) {
      return DeviceClassKey.readDeviceClassKey(type, len, sourceid, in);
    }
    if(deviceClassType == DeviceClassType.BUTTON) {
      return DeviceClassButton.readDeviceClassButton(type, len, sourceid, in);
    }
    if(deviceClassType == DeviceClassType.VALUATOR) {
      return DeviceClassValuator.readDeviceClassValuator(type, len, sourceid, in);
    }
    if(deviceClassType == DeviceClassType.SCROLL) {
      return DeviceClassScroll.readDeviceClassScroll(type, len, sourceid, in);
    }
    if(deviceClassType == DeviceClassType.TOUCH) {
      return DeviceClassTouch.readDeviceClassTouch(type, len, sourceid, in);
    }
    throw new IllegalStateException("Could not find class for " + deviceClassType);
  }
}
