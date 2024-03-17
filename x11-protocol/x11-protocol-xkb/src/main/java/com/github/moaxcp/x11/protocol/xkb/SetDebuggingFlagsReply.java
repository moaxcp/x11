package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetDebuggingFlagsReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";

  private short sequenceNumber;

  private int currentFlags;

  private int currentCtrls;

  private int supportedFlags;

  private int supportedCtrls;

  public static SetDebuggingFlagsReply readSetDebuggingFlagsReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    SetDebuggingFlagsReply.SetDebuggingFlagsReplyBuilder javaBuilder = SetDebuggingFlagsReply.builder();
    int length = in.readCard32();
    int currentFlags = in.readCard32();
    int currentCtrls = in.readCard32();
    int supportedFlags = in.readCard32();
    int supportedCtrls = in.readCard32();
    byte[] pad8 = in.readPad(8);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.currentFlags(currentFlags);
    javaBuilder.currentCtrls(currentCtrls);
    javaBuilder.supportedFlags(supportedFlags);
    javaBuilder.supportedCtrls(supportedCtrls);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(currentFlags);
    out.writeCard32(currentCtrls);
    out.writeCard32(supportedFlags);
    out.writeCard32(supportedCtrls);
    out.writePad(8);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetDebuggingFlagsReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
