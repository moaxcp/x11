package com.github.moaxcp.x11client.protocol.xf86vidmode;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetPermissionsReply implements XReply, Xf86vidmodeObject {
  private short sequenceNumber;

  private int permissions;

  public static GetPermissionsReply readGetPermissionsReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetPermissionsReply.GetPermissionsReplyBuilder javaBuilder = GetPermissionsReply.builder();
    int length = in.readCard32();
    int permissions = in.readCard32();
    byte[] pad5 = in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.permissions(permissions);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(permissions);
    out.writePad(20);
  }

  public boolean isPermissionsEnabled(@NonNull Permission... maskEnums) {
    for(Permission m : maskEnums) {
      if(!m.isEnabled(permissions)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class GetPermissionsReplyBuilder {
    public boolean isPermissionsEnabled(@NonNull Permission... maskEnums) {
      for(Permission m : maskEnums) {
        if(!m.isEnabled(permissions)) {
          return false;
        }
      }
      return true;
    }

    public GetPermissionsReply.GetPermissionsReplyBuilder permissionsEnable(
        Permission... maskEnums) {
      for(Permission m : maskEnums) {
        permissions((int) m.enableFor(permissions));
      }
      return this;
    }

    public GetPermissionsReply.GetPermissionsReplyBuilder permissionsDisable(
        Permission... maskEnums) {
      for(Permission m : maskEnums) {
        permissions((int) m.disableFor(permissions));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
