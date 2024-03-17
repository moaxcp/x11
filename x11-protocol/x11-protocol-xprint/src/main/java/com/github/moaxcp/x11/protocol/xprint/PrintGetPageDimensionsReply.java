package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintGetPageDimensionsReply implements XReply {
  public static final String PLUGIN_NAME = "xprint";

  private short sequenceNumber;

  private short width;

  private short height;

  private short offsetX;

  private short offsetY;

  private short reproducibleWidth;

  private short reproducibleHeight;

  public static PrintGetPageDimensionsReply readPrintGetPageDimensionsReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    PrintGetPageDimensionsReply.PrintGetPageDimensionsReplyBuilder javaBuilder = PrintGetPageDimensionsReply.builder();
    int length = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    short offsetX = in.readCard16();
    short offsetY = in.readCard16();
    short reproducibleWidth = in.readCard16();
    short reproducibleHeight = in.readCard16();
    in.readPad(12);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.offsetX(offsetX);
    javaBuilder.offsetY(offsetY);
    javaBuilder.reproducibleWidth(reproducibleWidth);
    javaBuilder.reproducibleHeight(reproducibleHeight);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(offsetX);
    out.writeCard16(offsetY);
    out.writeCard16(reproducibleWidth);
    out.writeCard16(reproducibleHeight);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintGetPageDimensionsReplyBuilder {
    public int getSize() {
      return 20;
    }
  }
}
