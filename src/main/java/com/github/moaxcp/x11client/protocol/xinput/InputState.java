package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XStruct;

import java.io.IOException;

public interface InputState extends XStruct {
  static InputState readInputState(X11Input in) throws IOException {
    byte classId = in.readCard8();
    byte len = in.readCard8();
    InputClass ref = InputClass.getByCode(classId);
    if(ref == InputClass.KEY) {
      return InputStateKey.readInputStateKey(classId, len, in);
    }
    if(ref == InputClass.BUTTON) {
      return InputStateButton.readInputStateButton(classId, len, in);
    }
    if(ref == InputClass.VALUATOR) {
      return InputStateValuator.readInputStateValuator(classId, len, in);
    }
    throw new IllegalStateException("Could not find class for " + ref);
  }
}
