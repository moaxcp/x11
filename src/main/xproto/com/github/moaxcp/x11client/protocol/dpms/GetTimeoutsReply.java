package com.github.moaxcp.x11client.protocol.dpms;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetTimeoutsReply implements XReply, DpmsObject {
  private short sequenceNumber;

  private short standbyTimeout;

  private short suspendTimeout;

  private short offTimeout;

  public static GetTimeoutsReply readGetTimeoutsReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    GetTimeoutsReply.GetTimeoutsReplyBuilder javaBuilder = GetTimeoutsReply.builder();
    int length = in.readCard32();
    short standbyTimeout = in.readCard16();
    short suspendTimeout = in.readCard16();
    short offTimeout = in.readCard16();
    byte[] pad7 = in.readPad(18);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.standbyTimeout(standbyTimeout);
    javaBuilder.suspendTimeout(suspendTimeout);
    javaBuilder.offTimeout(offTimeout);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(standbyTimeout);
    out.writeCard16(suspendTimeout);
    out.writeCard16(offTimeout);
    out.writePad(18);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class GetTimeoutsReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
