package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintSetImageResolutionReply implements XReply {
  public static final String PLUGIN_NAME = "xprint";

  private boolean status;

  private short sequenceNumber;

  private short previousResolutions;

  public static PrintSetImageResolutionReply readPrintSetImageResolutionReply(byte status,
      short sequenceNumber, X11Input in) throws IOException {
    PrintSetImageResolutionReply.PrintSetImageResolutionReplyBuilder javaBuilder = PrintSetImageResolutionReply.builder();
    int length = in.readCard32();
    short previousResolutions = in.readCard16();
    in.readPad(22);
    javaBuilder.status(status > 0);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.previousResolutions(previousResolutions);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeBool(status);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(previousResolutions);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 10;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintSetImageResolutionReplyBuilder {
    public int getSize() {
      return 10;
    }
  }
}
