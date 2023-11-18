package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetCrtcGammaSizeReply implements XReply, RandrObject {
  private short sequenceNumber;

  private short size;

  public static GetCrtcGammaSizeReply readGetCrtcGammaSizeReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetCrtcGammaSizeReply.GetCrtcGammaSizeReplyBuilder javaBuilder = GetCrtcGammaSizeReply.builder();
    int length = in.readCard32();
    short size = in.readCard16();
    byte[] pad5 = in.readPad(22);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.size(size);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(size);
    out.writePad(22);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class GetCrtcGammaSizeReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
