package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XStruct;

import java.io.IOException;

public interface InputInfo extends XStruct {
  static InputInfo readInputInfo(X11Input in) throws IOException {
    byte classId = in.readCard8();
    byte len = in.readCard8();
    InputClass ref = InputClass.getByCode(classId);
    if(ref == InputClass.KEY) {
      return InputInfoKey.readInputInfoKey(classId, len, in);
    }
    if(ref == InputClass.BUTTON) {
      return InputInfoButton.readInputInfoButton(classId, len, in);
    }
    if(ref == InputClass.VALUATOR) {
      return InputInfoValuator.readInputInfoValuator(classId, len, in);
    }
    throw new IllegalStateException("Could not find class for " + ref);
  }
}
