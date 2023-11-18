package com.github.moaxcp.x11client.protocol.screensaver;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryInfo implements TwoWayRequest<QueryInfoReply>, ScreensaverObject {
  public static final byte OPCODE = 1;

  private int drawable;

  public XReplyFunction<QueryInfoReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryInfoReply.readQueryInfoReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryInfo readQueryInfo(X11Input in) throws IOException {
    QueryInfo.QueryInfoBuilder javaBuilder = QueryInfo.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int drawable = in.readCard32();
    javaBuilder.drawable(drawable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class QueryInfoBuilder {
    public int getSize() {
      return 8;
    }
  }
}
