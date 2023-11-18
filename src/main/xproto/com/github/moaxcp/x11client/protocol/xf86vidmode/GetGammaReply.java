package com.github.moaxcp.x11client.protocol.xf86vidmode;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetGammaReply implements XReply, Xf86vidmodeObject {
  private short sequenceNumber;

  private int red;

  private int green;

  private int blue;

  public static GetGammaReply readGetGammaReply(byte pad1, short sequenceNumber, X11Input in) throws
      IOException {
    GetGammaReply.GetGammaReplyBuilder javaBuilder = GetGammaReply.builder();
    int length = in.readCard32();
    int red = in.readCard32();
    int green = in.readCard32();
    int blue = in.readCard32();
    byte[] pad7 = in.readPad(12);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(red);
    out.writeCard32(green);
    out.writeCard32(blue);
    out.writePad(12);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class GetGammaReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
