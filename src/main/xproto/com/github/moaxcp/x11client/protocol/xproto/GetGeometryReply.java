package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetGeometryReply implements XReply, XprotoObject {
  private byte depth;

  private short sequenceNumber;

  private int root;

  private short x;

  private short y;

  private short width;

  private short height;

  private short borderWidth;

  public static GetGeometryReply readGetGeometryReply(byte depth, short sequenceNumber, X11Input in)
      throws IOException {
    GetGeometryReply.GetGeometryReplyBuilder javaBuilder = GetGeometryReply.builder();
    int length = in.readCard32();
    int root = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    short borderWidth = in.readCard16();
    byte[] pad10 = in.readPad(2);
    in.readPad(8);
    javaBuilder.depth(depth);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.root(root);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.borderWidth(borderWidth);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(depth);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(root);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(borderWidth);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public static class GetGeometryReplyBuilder {
    public int getSize() {
      return 24;
    }
  }
}
