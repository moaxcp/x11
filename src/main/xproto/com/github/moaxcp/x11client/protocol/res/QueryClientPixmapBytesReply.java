package com.github.moaxcp.x11client.protocol.res;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryClientPixmapBytesReply implements XReply, ResObject {
  private short sequenceNumber;

  private int bytes;

  private int bytesOverflow;

  public static QueryClientPixmapBytesReply readQueryClientPixmapBytesReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    QueryClientPixmapBytesReply.QueryClientPixmapBytesReplyBuilder javaBuilder = QueryClientPixmapBytesReply.builder();
    int length = in.readCard32();
    int bytes = in.readCard32();
    int bytesOverflow = in.readCard32();
    in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.bytes(bytes);
    javaBuilder.bytesOverflow(bytesOverflow);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(bytes);
    out.writeCard32(bytesOverflow);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class QueryClientPixmapBytesReplyBuilder {
    public int getSize() {
      return 16;
    }
  }
}
