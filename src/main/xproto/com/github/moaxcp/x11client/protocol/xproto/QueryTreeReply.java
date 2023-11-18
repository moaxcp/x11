package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class QueryTreeReply implements XReply, XprotoObject {
  private short sequenceNumber;

  private int root;

  private int parent;

  @NonNull
  private List<Integer> children;

  public static QueryTreeReply readQueryTreeReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    QueryTreeReply.QueryTreeReplyBuilder javaBuilder = QueryTreeReply.builder();
    int length = in.readCard32();
    int root = in.readCard32();
    int parent = in.readCard32();
    short childrenLen = in.readCard16();
    byte[] pad7 = in.readPad(14);
    List<Integer> children = in.readCard32(Short.toUnsignedInt(childrenLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.root(root);
    javaBuilder.parent(parent);
    javaBuilder.children(children);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(root);
    out.writeCard32(parent);
    short childrenLen = (short) children.size();
    out.writeCard16(childrenLen);
    out.writePad(14);
    out.writeCard32(children);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * children.size();
  }

  public static class QueryTreeReplyBuilder {
    public int getSize() {
      return 32 + 4 * children.size();
    }
  }
}
