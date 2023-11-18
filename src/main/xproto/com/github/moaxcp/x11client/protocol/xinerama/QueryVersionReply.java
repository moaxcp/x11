package com.github.moaxcp.x11client.protocol.xinerama;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryVersionReply implements XReply, XineramaObject {
  private short sequenceNumber;

  private short major;

  private short minor;

  public static QueryVersionReply readQueryVersionReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryVersionReply.QueryVersionReplyBuilder javaBuilder = QueryVersionReply.builder();
    int length = in.readCard32();
    short major = in.readCard16();
    short minor = in.readCard16();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.major(major);
    javaBuilder.minor(minor);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(major);
    out.writeCard16(minor);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class QueryVersionReplyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
