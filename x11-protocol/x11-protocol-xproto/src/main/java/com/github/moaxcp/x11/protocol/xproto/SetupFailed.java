package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class SetupFailed implements XStruct {
  public static final String PLUGIN_NAME = "xproto";

  private byte status;

  private short protocolMajorVersion;

  private short protocolMinorVersion;

  private short length;

  @NonNull
  private ByteList reason;

  public static SetupFailed readSetupFailed(X11Input in) throws IOException {
    SetupFailed.SetupFailedBuilder javaBuilder = SetupFailed.builder();
    byte status = in.readCard8();
    byte reasonLen = in.readCard8();
    short protocolMajorVersion = in.readCard16();
    short protocolMinorVersion = in.readCard16();
    short length = in.readCard16();
    ByteList reason = in.readChar(Byte.toUnsignedInt(reasonLen));
    javaBuilder.status(status);
    javaBuilder.protocolMajorVersion(protocolMajorVersion);
    javaBuilder.protocolMinorVersion(protocolMinorVersion);
    javaBuilder.length(length);
    javaBuilder.reason(reason.toImmutable());
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetupFailedBuilder {
    public int getSize() {
      return 8 + 1 * reason.size();
    }
  }
}
