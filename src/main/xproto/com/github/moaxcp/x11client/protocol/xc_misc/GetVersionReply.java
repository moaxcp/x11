package com.github.moaxcp.x11client.protocol.xc_misc;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetVersionReply implements XReply, XcMiscObject {
  private short sequenceNumber;

  private short serverMajorVersion;

  private short serverMinorVersion;

  public static GetVersionReply readGetVersionReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    GetVersionReply.GetVersionReplyBuilder javaBuilder = GetVersionReply.builder();
    int length = in.readCard32();
    short serverMajorVersion = in.readCard16();
    short serverMinorVersion = in.readCard16();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.serverMajorVersion(serverMajorVersion);
    javaBuilder.serverMinorVersion(serverMinorVersion);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(serverMajorVersion);
    out.writeCard16(serverMinorVersion);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GetVersionReplyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
