package com.github.moaxcp.x11client.protocol.dpms;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CapableReply implements XReply, DpmsObject {
  private short sequenceNumber;

  private boolean capable;

  public static CapableReply readCapableReply(byte pad1, short sequenceNumber, X11Input in) throws
      IOException {
    CapableReply.CapableReplyBuilder javaBuilder = CapableReply.builder();
    int length = in.readCard32();
    boolean capable = in.readBool();
    byte[] pad5 = in.readPad(23);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.capable(capable);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeBool(capable);
    out.writePad(23);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class CapableReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
