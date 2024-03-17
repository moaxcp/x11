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
public class QueryPointer implements TwoWayRequest<QueryPointerReply> {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 38;

  private int window;

  public XReplyFunction<QueryPointerReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryPointerReply.readQueryPointerReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryPointer readQueryPointer(X11Input in) throws IOException {
    QueryPointer.QueryPointerBuilder javaBuilder = QueryPointer.builder();
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

  public static class QueryPointerBuilder {
    public int getSize() {
      return 8;
    }
  }
}
