package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetScreenSizeRangeReply implements XReply {
  public static final String PLUGIN_NAME = "randr";

  private short sequenceNumber;

  private short minWidth;

  private short minHeight;

  private short maxWidth;

  private short maxHeight;

  public static GetScreenSizeRangeReply readGetScreenSizeRangeReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetScreenSizeRangeReply.GetScreenSizeRangeReplyBuilder javaBuilder = GetScreenSizeRangeReply.builder();
    int length = in.readCard32();
    short minWidth = in.readCard16();
    short minHeight = in.readCard16();
    short maxWidth = in.readCard16();
    short maxHeight = in.readCard16();
    byte[] pad8 = in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.minWidth(minWidth);
    javaBuilder.minHeight(minHeight);
    javaBuilder.maxWidth(maxWidth);
    javaBuilder.maxHeight(maxHeight);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(minWidth);
    out.writeCard16(minHeight);
    out.writeCard16(maxWidth);
    out.writeCard16(maxHeight);
    out.writePad(16);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetScreenSizeRangeReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
