package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintInputSelectedReply implements XReply, XprintObject {
  private short sequenceNumber;

  private int eventMask;

  private int allEventsMask;

  public static PrintInputSelectedReply readPrintInputSelectedReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    PrintInputSelectedReply.PrintInputSelectedReplyBuilder javaBuilder = PrintInputSelectedReply.builder();
    int length = in.readCard32();
    int eventMask = in.readCard32();
    int allEventsMask = in.readCard32();
    in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventMask(eventMask);
    javaBuilder.allEventsMask(allEventsMask);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(eventMask);
    out.writeCard32(allEventsMask);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class PrintInputSelectedReplyBuilder {
    public int getSize() {
      return 16;
    }
  }
}
