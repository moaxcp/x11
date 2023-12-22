package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.XStruct;

import java.io.IOException;

public interface HierarchyChange extends XStruct {
  static HierarchyChange readHierarchyChange(X11Input in) throws IOException {
    short type = in.readCard16();
    short len = in.readCard16();
    HierarchyChangeType ref = HierarchyChangeType.getByCode(type);
    if(ref == HierarchyChangeType.ADD_MASTER) {
      return HierarchyChangeAddMaster.readHierarchyChangeAddMaster(type, len, in);
    }
    if(ref == HierarchyChangeType.REMOVE_MASTER) {
      return HierarchyChangeRemoveMaster.readHierarchyChangeRemoveMaster(type, len, in);
    }
    if(ref == HierarchyChangeType.ATTACH_SLAVE) {
      return HierarchyChangeAttachSlave.readHierarchyChangeAttachSlave(type, len, in);
    }
    if(ref == HierarchyChangeType.DETACH_SLAVE) {
      return HierarchyChangeDetachSlave.readHierarchyChangeDetachSlave(type, len, in);
    }
    throw new IllegalStateException("Could not find class for " + ref);
  }
}
