package com.github.moaxcp.x11client.protocol.dri3;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryVersionReply implements XReply, Dri3Object {
  private short sequenceNumber;

  private int majorVersion;

  private int minorVersion;

  public static QueryVersionReply readQueryVersionReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryVersionReply.QueryVersionReplyBuilder javaBuilder = QueryVersionReply.builder();
    int length = in.readCard32();
    int majorVersion = in.readCard32();
    int minorVersion = in.readCard32();
    in.readPad(16);
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
    out.writeCard32(majorVersion);
    out.writeCard32(minorVersion);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class QueryVersionReplyBuilder {
    public int getSize() {
      return 16;
    }
  }
}
