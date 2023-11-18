package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryCounter implements TwoWayRequest<QueryCounterReply>, SyncObject {
  public static final byte OPCODE = 5;

  private int counter;

  public XReplyFunction<QueryCounterReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryCounterReply.readQueryCounterReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryCounter readQueryCounter(X11Input in) throws IOException {
    QueryCounter.QueryCounterBuilder javaBuilder = QueryCounter.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int counter = in.readCard32();
    javaBuilder.counter(counter);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(counter);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class QueryCounterBuilder {
    public int getSize() {
      return 8;
    }
  }
}
