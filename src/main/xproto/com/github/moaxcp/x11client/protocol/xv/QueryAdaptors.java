package com.github.moaxcp.x11client.protocol.xv;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryAdaptors implements TwoWayRequest<QueryAdaptorsReply>, XvObject {
  public static final byte OPCODE = 1;

  private int window;

  public XReplyFunction<QueryAdaptorsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryAdaptorsReply.readQueryAdaptorsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryAdaptors readQueryAdaptors(X11Input in) throws IOException {
    QueryAdaptors.QueryAdaptorsBuilder javaBuilder = QueryAdaptors.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class QueryAdaptorsBuilder {
    public int getSize() {
      return 8;
    }
  }
}
