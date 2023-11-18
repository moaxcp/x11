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
public class SetupAuthenticate implements XStruct, XprotoObject {
  private byte status;

  @NonNull
  private List<Byte> reason;

  public static SetupAuthenticate readSetupAuthenticate(X11Input in) throws IOException {
    SetupAuthenticate.SetupAuthenticateBuilder javaBuilder = SetupAuthenticate.builder();
    byte status = in.readCard8();
    byte[] pad1 = in.readPad(5);
    short length = in.readCard16();
    List<Byte> reason = in.readChar(Short.toUnsignedInt(length) * 4);
    javaBuilder.status(status);
    javaBuilder.reason(reason);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(status);
    out.writePad(5);
    short length = (short) reason.size();
    out.writeCard16(length);
    out.writeChar(reason);
  }

  @Override
  public int getSize() {
    return 8 + 1 * reason.size();
  }

  public static class SetupAuthenticateBuilder {
    public int getSize() {
      return 8 + 1 * reason.size();
    }
  }
}
