package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public interface BehaviorUnion {
  static BehaviorUnion readBehaviorUnion(X11Input in) throws IOException {
    byte type = in.readCard8();
    byte data = in.readCard8();
    BehaviorType typeEnum = BehaviorType.getByCode(type);
    if(typeEnum == BehaviorType.DEFAULT || typeEnum == BehaviorType.LOCK || typeEnum == BehaviorType.PERMAMENT_LOCK) {
      return new DefaultBehavior(type);
    }
    if(typeEnum == BehaviorType.RADIO_GROUP || typeEnum == BehaviorType.PERMAMENT_RADIO_GROUP) {
      return new RadioGroupBehavior(type, data);
    }
    if(typeEnum == BehaviorType.OVERLAY1 || typeEnum == BehaviorType.OVERLAY2 || typeEnum == BehaviorType.PERMAMENT_OVERLAY1 || typeEnum == BehaviorType.PERMAMENT_OVERLAY2) {
      return new OverlayBehavior(type, data);
    }
    else {
      return new CommonBehavior(type, data);
    }
  }

  void write(X11Output out) throws IOException;
}
