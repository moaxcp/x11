package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintGetImageResolutionReply implements XReply, XprintObject {
  private short sequenceNumber;

  private short imageResolution;

  public static PrintGetImageResolutionReply readPrintGetImageResolutionReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    PrintGetImageResolutionReply.PrintGetImageResolutionReplyBuilder javaBuilder = PrintGetImageResolutionReply.builder();
    int length = in.readCard32();
    short imageResolution = in.readCard16();
    in.readPad(22);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.imageResolution(imageResolution);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(imageResolution);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 10;
  }

  public static class PrintGetImageResolutionReplyBuilder {
    public int getSize() {
      return 10;
    }
  }
}
