package com.github.moaxcp.x11client.protocol.xvmc;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryVersionReply implements XReply, XvmcObject {
  private short sequenceNumber;

  private int major;

  private int minor;

  public static QueryVersionReply readQueryVersionReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryVersionReply.QueryVersionReplyBuilder javaBuilder = QueryVersionReply.builder();
    int length = in.readCard32();
    int major = in.readCard32();
    int minor = in.readCard32();
    in.readPad(16);
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
    out.writeCard32(major);
    out.writeCard32(minor);
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
