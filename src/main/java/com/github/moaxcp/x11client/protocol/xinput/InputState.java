package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XStruct;

public interface InputState extends XStruct {
  static InputState readInputState(X11Input in) {
    return null;
  }
}
