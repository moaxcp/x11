package com.github.moaxcp.x11client.protocol.bigreq;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EnableReply implements XReply, BigreqObject {
  private short sequenceNumber;

  private int maximumRequestLength;

  public static EnableReply readEnableReply(byte pad1, short sequenceNumber, X11Input in) throws
      IOException {
    EnableReply.EnableReplyBuilder javaBuilder = EnableReply.builder();
    int length = in.readCard32();
    int maximumRequestLength = in.readCard32();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.maximumRequestLength(maximumRequestLength);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(maximumRequestLength);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class EnableReplyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
