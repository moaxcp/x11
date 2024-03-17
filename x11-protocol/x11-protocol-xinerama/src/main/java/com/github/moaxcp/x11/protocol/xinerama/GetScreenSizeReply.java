package com.github.moaxcp.x11.protocol.xinerama;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetScreenSizeReply implements XReply {
  public static final String PLUGIN_NAME = "xinerama";

  private short sequenceNumber;

  private int width;

  private int height;

  private int window;

  private int screen;

  public static GetScreenSizeReply readGetScreenSizeReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetScreenSizeReply.GetScreenSizeReplyBuilder javaBuilder = GetScreenSizeReply.builder();
    int length = in.readCard32();
    int width = in.readCard32();
    int height = in.readCard32();
    int window = in.readCard32();
    int screen = in.readCard32();
    in.readPad(8);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.window(window);
    javaBuilder.screen(screen);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(width);
    out.writeCard32(height);
    out.writeCard32(window);
    out.writeCard32(screen);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetScreenSizeReplyBuilder {
    public int getSize() {
      return 24;
    }
  }
}
