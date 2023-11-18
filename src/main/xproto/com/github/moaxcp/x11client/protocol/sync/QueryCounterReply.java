package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class QueryCounterReply implements XReply, SyncObject {
  private short sequenceNumber;

  @NonNull
  private Int64 counterValue;

  public static QueryCounterReply readQueryCounterReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryCounterReply.QueryCounterReplyBuilder javaBuilder = QueryCounterReply.builder();
    int length = in.readCard32();
    Int64 counterValue = Int64.readInt64(in);
    in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.counterValue(counterValue);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    counterValue.write(out);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class QueryCounterReplyBuilder {
    public int getSize() {
      return 16;
    }
  }
}
