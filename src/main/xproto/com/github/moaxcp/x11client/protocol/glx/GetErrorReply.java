package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetErrorReply implements XReply, GlxObject {
  private short sequenceNumber;

  private int error;

  public static GetErrorReply readGetErrorReply(byte pad1, short sequenceNumber, X11Input in) throws
      IOException {
    GetErrorReply.GetErrorReplyBuilder javaBuilder = GetErrorReply.builder();
    int length = in.readCard32();
    int error = in.readInt32();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.error(error);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeInt32(error);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GetErrorReplyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
