package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryFenceReply implements XReply, SyncObject {
  private short sequenceNumber;

  private boolean triggered;

  public static QueryFenceReply readQueryFenceReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    QueryFenceReply.QueryFenceReplyBuilder javaBuilder = QueryFenceReply.builder();
    int length = in.readCard32();
    boolean triggered = in.readBool();
    byte[] pad5 = in.readPad(23);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.triggered(triggered);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeBool(triggered);
    out.writePad(23);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class QueryFenceReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
