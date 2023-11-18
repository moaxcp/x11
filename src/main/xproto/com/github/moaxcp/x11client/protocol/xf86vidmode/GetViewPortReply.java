package com.github.moaxcp.x11client.protocol.xf86vidmode;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetViewPortReply implements XReply, Xf86vidmodeObject {
  private short sequenceNumber;

  private int x;

  private int y;

  public static GetViewPortReply readGetViewPortReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    GetViewPortReply.GetViewPortReplyBuilder javaBuilder = GetViewPortReply.builder();
    int length = in.readCard32();
    int x = in.readCard32();
    int y = in.readCard32();
    byte[] pad6 = in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.x(x);
    javaBuilder.y(y);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(x);
    out.writeCard32(y);
    out.writePad(16);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class GetViewPortReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
