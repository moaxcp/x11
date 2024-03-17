package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LookupColorReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  private short exactRed;

  private short exactGreen;

  private short exactBlue;

  private short visualRed;

  private short visualGreen;

  private short visualBlue;

  public static LookupColorReply readLookupColorReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    LookupColorReply.LookupColorReplyBuilder javaBuilder = LookupColorReply.builder();
    int length = in.readCard32();
    short exactRed = in.readCard16();
    short exactGreen = in.readCard16();
    short exactBlue = in.readCard16();
    short visualRed = in.readCard16();
    short visualGreen = in.readCard16();
    short visualBlue = in.readCard16();
    in.readPad(12);
    javaBuilder.sequenceNumber(sequenceNumber);
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
    out.writeCard16(exactRed);
    out.writeCard16(exactGreen);
    out.writeCard16(exactBlue);
    out.writeCard16(visualRed);
    out.writeCard16(visualGreen);
    out.writeCard16(visualBlue);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class LookupColorReplyBuilder {
    public int getSize() {
      return 20;
    }
  }
}
