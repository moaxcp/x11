package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class QueryTreeReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  private int root;

  private int parent;

  @NonNull
  private IntList children;

  public static QueryTreeReply readQueryTreeReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    QueryTreeReply.QueryTreeReplyBuilder javaBuilder = QueryTreeReply.builder();
    int length = in.readCard32();
    int root = in.readCard32();
    int parent = in.readCard32();
    short childrenLen = in.readCard16();
    byte[] pad7 = in.readPad(14);
    IntList children = in.readCard32(Short.toUnsignedInt(childrenLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.root(root);
    javaBuilder.parent(parent);
    javaBuilder.children(children.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryTreeReplyBuilder {
    public int getSize() {
      return 32 + 4 * children.size();
    }
  }
}
