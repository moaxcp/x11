package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintSetImageResolution implements TwoWayRequest<PrintSetImageResolutionReply> {
  public static final String PLUGIN_NAME = "xprint";

  public static final byte OPCODE = 23;

  private int context;

  private short imageResolution;

  public XReplyFunction<PrintSetImageResolutionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> PrintSetImageResolutionReply.readPrintSetImageResolutionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintSetImageResolution readPrintSetImageResolution(X11Input in) throws
      IOException {
    PrintSetImageResolution.PrintSetImageResolutionBuilder javaBuilder = PrintSetImageResolution.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int context = in.readCard32();
    short imageResolution = in.readCard16();
    javaBuilder.context(context);
    javaBuilder.imageResolution(imageResolution);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(context);
    out.writeCard16(imageResolution);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 10;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintSetImageResolutionBuilder {
    public int getSize() {
      return 10;
    }
  }
}
