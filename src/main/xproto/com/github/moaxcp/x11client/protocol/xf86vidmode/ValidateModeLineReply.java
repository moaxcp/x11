package com.github.moaxcp.x11client.protocol.xf86vidmode;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ValidateModeLineReply implements XReply, Xf86vidmodeObject {
  private short sequenceNumber;

  private int status;

  public static ValidateModeLineReply readValidateModeLineReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    ValidateModeLineReply.ValidateModeLineReplyBuilder javaBuilder = ValidateModeLineReply.builder();
    int length = in.readCard32();
    int status = in.readCard32();
    byte[] pad5 = in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.status(status);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(status);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class ValidateModeLineReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
