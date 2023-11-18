package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryBestSizeReply implements XReply, XprotoObject {
  private short sequenceNumber;

  private short width;

  private short height;

  public static QueryBestSizeReply readQueryBestSizeReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryBestSizeReply.QueryBestSizeReplyBuilder javaBuilder = QueryBestSizeReply.builder();
    int length = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.width(width);
    javaBuilder.height(height);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(width);
    out.writeCard16(height);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class QueryBestSizeReplyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
