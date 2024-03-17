package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintQueryVersionReply implements XReply {
  public static final String PLUGIN_NAME = "xprint";

  private short sequenceNumber;

  private short majorVersion;

  private short minorVersion;

  public static PrintQueryVersionReply readPrintQueryVersionReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    PrintQueryVersionReply.PrintQueryVersionReplyBuilder javaBuilder = PrintQueryVersionReply.builder();
    int length = in.readCard32();
    short majorVersion = in.readCard16();
    short minorVersion = in.readCard16();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.majorVersion(majorVersion);
    javaBuilder.minorVersion(minorVersion);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(majorVersion);
    out.writeCard16(minorVersion);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintQueryVersionReplyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
