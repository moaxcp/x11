package com.github.moaxcp.x11.protocol.xinerama;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class IsActiveReply implements XReply {
  public static final String PLUGIN_NAME = "xinerama";

  private short sequenceNumber;

  private int state;

  public static IsActiveReply readIsActiveReply(byte pad1, short sequenceNumber, X11Input in) throws
      IOException {
    IsActiveReply.IsActiveReplyBuilder javaBuilder = IsActiveReply.builder();
    int length = in.readCard32();
    int state = in.readCard32();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.state(state);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(state);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class IsActiveReplyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
