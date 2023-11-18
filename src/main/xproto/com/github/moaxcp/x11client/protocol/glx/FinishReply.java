package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FinishReply implements XReply, GlxObject {
  private short sequenceNumber;

  public static FinishReply readFinishReply(byte pad1, short sequenceNumber, X11Input in) throws
      IOException {
    FinishReply.FinishReplyBuilder javaBuilder = FinishReply.builder();
    int length = in.readCard32();
    in.readPad(24);
    javaBuilder.sequenceNumber(sequenceNumber);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class FinishReplyBuilder {
    public int getSize() {
      return 8;
    }
  }
}
