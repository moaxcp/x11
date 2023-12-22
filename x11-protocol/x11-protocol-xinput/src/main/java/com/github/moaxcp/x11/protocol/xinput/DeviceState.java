package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.XStruct;

import java.io.IOException;

public interface DeviceState extends XStruct {
  static DeviceState readDeviceState(X11Input in) throws IOException {
    short controlId = in.readCard16();
    short len = in.readCard16();
    DeviceControl ref = DeviceControl.getByCode(controlId);
    if(ref == DeviceControl.RESOLUTION) {
      return DeviceStateResolution.readDeviceStateResolution(controlId, len, in);
    }
    if(ref == DeviceControl.ABS_CALIB) {
      return DeviceStateAbsCalib.readDeviceStateAbsCalib(controlId, len, in);
    }
    if(ref == DeviceControl.CORE) {
      return DeviceStateCore.readDeviceStateCore(controlId, len, in);
    }
    if(ref == DeviceControl.ENABLE) {
      return DeviceStateEnable.readDeviceStateEnable(controlId, len, in);
    }
    if(ref == DeviceControl.ABS_AREA) {
      return DeviceStateAbsArea.readDeviceStateAbsArea(controlId, len, in);
    }
    throw new IllegalStateException("Could not find class for " + ref);
  }
}
