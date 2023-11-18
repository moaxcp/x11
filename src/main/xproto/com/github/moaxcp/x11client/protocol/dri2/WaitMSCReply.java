package com.github.moaxcp.x11client.protocol.dri2;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WaitMSCReply implements XReply, Dri2Object {
  private short sequenceNumber;

  private int ustHi;

  private int ustLo;

  private int mscHi;

  private int mscLo;

  private int sbcHi;

  private int sbcLo;

  public static WaitMSCReply readWaitMSCReply(byte pad1, short sequenceNumber, X11Input in) throws
      IOException {
    WaitMSCReply.WaitMSCReplyBuilder javaBuilder = WaitMSCReply.builder();
    int length = in.readCard32();
    int ustHi = in.readCard32();
    int ustLo = in.readCard32();
    int mscHi = in.readCard32();
    int mscLo = in.readCard32();
    int sbcHi = in.readCard32();
    int sbcLo = in.readCard32();
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.ustHi(ustHi);
    javaBuilder.ustLo(ustLo);
    javaBuilder.mscHi(mscHi);
    javaBuilder.mscLo(mscLo);
    javaBuilder.sbcHi(sbcHi);
    javaBuilder.sbcLo(sbcLo);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(ustHi);
    out.writeCard32(ustLo);
    out.writeCard32(mscHi);
    out.writeCard32(mscLo);
    out.writeCard32(sbcHi);
    out.writeCard32(sbcLo);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class WaitMSCReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
