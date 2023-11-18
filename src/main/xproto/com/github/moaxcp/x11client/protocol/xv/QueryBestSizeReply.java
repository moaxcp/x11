package com.github.moaxcp.x11client.protocol.xv;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryBestSizeReply implements XReply, XvObject {
  private short sequenceNumber;

  private short actualWidth;

  private short actualHeight;

  public static QueryBestSizeReply readQueryBestSizeReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryBestSizeReply.QueryBestSizeReplyBuilder javaBuilder = QueryBestSizeReply.builder();
    int length = in.readCard32();
    short actualWidth = in.readCard16();
    short actualHeight = in.readCard16();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.actualWidth(actualWidth);
    javaBuilder.actualHeight(actualHeight);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(actualWidth);
    out.writeCard16(actualHeight);
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
