package com.github.moaxcp.x11client.protocol.xevie;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StartReply implements XReply, XevieObject {
  private short sequenceNumber;

  public static StartReply readStartReply(byte pad1, short sequenceNumber, X11Input in) throws
      IOException {
    StartReply.StartReplyBuilder javaBuilder = StartReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(24);
    javaBuilder.sequenceNumber(sequenceNumber);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writePad(24);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class StartReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
