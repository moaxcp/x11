package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AllocNamedColorReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  private int pixel;

  private short exactRed;

  private short exactGreen;

  private short exactBlue;

  private short visualRed;

  private short visualGreen;

  private short visualBlue;

  public static AllocNamedColorReply readAllocNamedColorReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    AllocNamedColorReply.AllocNamedColorReplyBuilder javaBuilder = AllocNamedColorReply.builder();
    int length = in.readCard32();
    int pixel = in.readCard32();
    short exactRed = in.readCard16();
    short exactGreen = in.readCard16();
    short exactBlue = in.readCard16();
    short visualRed = in.readCard16();
    short visualGreen = in.readCard16();
    short visualBlue = in.readCard16();
    in.readPad(8);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.pixel(pixel);
    javaBuilder.exactRed(exactRed);
    javaBuilder.exactGreen(exactGreen);
    javaBuilder.exactBlue(exactBlue);
    javaBuilder.visualRed(visualRed);
    javaBuilder.visualGreen(visualGreen);
    javaBuilder.visualBlue(visualBlue);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(pixel);
    out.writeCard16(exactRed);
    out.writeCard16(exactGreen);
    out.writeCard16(exactBlue);
    out.writeCard16(visualRed);
    out.writeCard16(visualGreen);
    out.writeCard16(visualBlue);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class AllocNamedColorReplyBuilder {
    public int getSize() {
      return 24;
    }
  }
}
