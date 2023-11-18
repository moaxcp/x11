package com.github.moaxcp.x11client.protocol.composite;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetOverlayWindowReply implements XReply, CompositeObject {
  private short sequenceNumber;

  private int overlayWin;

  public static GetOverlayWindowReply readGetOverlayWindowReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetOverlayWindowReply.GetOverlayWindowReplyBuilder javaBuilder = GetOverlayWindowReply.builder();
    int length = in.readCard32();
    int overlayWin = in.readCard32();
    byte[] pad5 = in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.overlayWin(overlayWin);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(overlayWin);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class GetOverlayWindowReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
