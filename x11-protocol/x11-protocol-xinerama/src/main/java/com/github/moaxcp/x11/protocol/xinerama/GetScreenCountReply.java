package com.github.moaxcp.x11.protocol.xinerama;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetScreenCountReply implements XReply {
  public static final String PLUGIN_NAME = "xinerama";

  private byte screenCount;

  private short sequenceNumber;

  private int window;

  public static GetScreenCountReply readGetScreenCountReply(byte screenCount, short sequenceNumber,
      X11Input in) throws IOException {
    GetScreenCountReply.GetScreenCountReplyBuilder javaBuilder = GetScreenCountReply.builder();
    int length = in.readCard32();
    int window = in.readCard32();
    in.readPad(20);
    javaBuilder.screenCount(screenCount);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeByte(screenCount);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetScreenCountReplyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
