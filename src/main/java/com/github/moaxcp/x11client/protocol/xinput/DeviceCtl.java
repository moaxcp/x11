package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XStruct;

import java.io.IOException;

public interface DeviceCtl extends XStruct {
  static DeviceCtl readDeviceCtl(X11Input in) throws IOException {
    short controlId = in.readCard16();
    short len = in.readCard16();
    DeviceControl deviceControl = DeviceControl.getByCode(controlId);
    if(deviceControl == DeviceControl.RESOLUTION) {
      return DeviceCtlResolution.readDeviceCtlResolution(controlId, len, in);
    }
    if(deviceControl == DeviceControl.ABS_CALIB) {
      return DeviceCtlAbs_calib.readDeviceCtlAbs_calib(controlId, len, in);
    }
    if(deviceControl == DeviceControl.CORE) {
      return DeviceCtlCore.readDeviceCtlCore(controlId, len, in);
    }
    if(deviceControl == DeviceControl.ENABLE) {
      return DeviceCtlEnable.readDeviceCtlEnable(controlId, len, in);
    }
    if(deviceControl == DeviceControl.ABS_AREA) {
      return DeviceCtlAbs_area.readDeviceCtlAbs_area(controlId, len, in);
    }
    throw new IllegalStateException("Could not find class for " + deviceControl);
  }
}
