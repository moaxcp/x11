package com.github.moaxcp.x11client.protocol.present;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryCapabilitiesReply implements XReply, PresentObject {
  private short sequenceNumber;

  private int capabilities;

  public static QueryCapabilitiesReply readQueryCapabilitiesReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryCapabilitiesReply.QueryCapabilitiesReplyBuilder javaBuilder = QueryCapabilitiesReply.builder();
    int length = in.readCard32();
    int capabilities = in.readCard32();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.capabilities(capabilities);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(capabilities);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class QueryCapabilitiesReplyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
