package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;

public interface NotifyDataUnion extends XObject {
  static NotifyDataUnion readNotifyDataUnion(X11Input in, byte subCode) throws IOException {
    switch(subCode) {
      case 0:
        return CrtcChange.readCrtcChange(in);
      case 1:
        return OutputChange.readOutputChange(in);
      case 2:
        return OutputProperty.readOutputProperty(in);
      case 3:
        return ProviderChange.readProviderChange(in);
      case 4:
        return ProviderProperty.readProviderProperty(in);
      case 5:
        return ResourceChange.readResourceChange(in);
      case 6:
        return LeaseNotify.readLeaseNotify(in);
      default:
        throw new IllegalArgumentException("implementation not found for subCode \"" + subCode + "\"");
    }
  }

  void write(X11Output in) throws IOException;
}
