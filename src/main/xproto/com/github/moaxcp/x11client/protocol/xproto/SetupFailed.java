package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetupFailed implements XStruct, XprotoObject {
  private byte status;

  private short protocolMajorVersion;

  private short protocolMinorVersion;

  private short length;

  @NonNull
  private List<Byte> reason;

  public static SetupFailed readSetupFailed(X11Input in) throws IOException {
    SetupFailed.SetupFailedBuilder javaBuilder = SetupFailed.builder();
    byte status = in.readCard8();
    byte reasonLen = in.readCard8();
    short protocolMajorVersion = in.readCard16();
    short protocolMinorVersion = in.readCard16();
    short length = in.readCard16();
    List<Byte> reason = in.readChar(Byte.toUnsignedInt(reasonLen));
    javaBuilder.status(status);
    javaBuilder.protocolMajorVersion(protocolMajorVersion);
    javaBuilder.protocolMinorVersion(protocolMinorVersion);
    javaBuilder.length(length);
    javaBuilder.reason(reason);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(status);
    byte reasonLen = (byte) reason.size();
    out.writeCard8(reasonLen);
    out.writeCard16(protocolMajorVersion);
    out.writeCard16(protocolMinorVersion);
    out.writeCard16(length);
    out.writeChar(reason);
  }

  @Override
  public int getSize() {
    return 8 + 1 * reason.size();
  }

  public static class SetupFailedBuilder {
    public int getSize() {
      return 8 + 1 * reason.size();
    }
  }
}
