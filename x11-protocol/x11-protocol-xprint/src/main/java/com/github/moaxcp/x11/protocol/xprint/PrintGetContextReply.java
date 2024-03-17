package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintGetContextReply implements XReply {
  public static final String PLUGIN_NAME = "xprint";

  private short sequenceNumber;

  private int context;

  public static PrintGetContextReply readPrintGetContextReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    PrintGetContextReply.PrintGetContextReplyBuilder javaBuilder = PrintGetContextReply.builder();
    int length = in.readCard32();
    int context = in.readCard32();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.context(context);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(context);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintGetContextReplyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
