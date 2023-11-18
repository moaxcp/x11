package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InitializeReply implements XReply, SyncObject {
  private short sequenceNumber;

  private byte majorVersion;

  private byte minorVersion;

  public static InitializeReply readInitializeReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    InitializeReply.InitializeReplyBuilder javaBuilder = InitializeReply.builder();
    int length = in.readCard32();
    byte majorVersion = in.readCard8();
    byte minorVersion = in.readCard8();
    byte[] pad6 = in.readPad(22);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.majorVersion(majorVersion);
    javaBuilder.minorVersion(minorVersion);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard8(majorVersion);
    out.writeCard8(minorVersion);
    out.writePad(22);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class InitializeReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
