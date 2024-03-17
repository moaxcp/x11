package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryTree implements TwoWayRequest<QueryTreeReply> {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 15;

  private int window;

  public XReplyFunction<QueryTreeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryTreeReply.readQueryTreeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryTree readQueryTree(X11Input in) throws IOException {
    QueryTree.QueryTreeBuilder javaBuilder = QueryTree.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryTreeBuilder {
    public int getSize() {
      return 8;
    }
  }
}
