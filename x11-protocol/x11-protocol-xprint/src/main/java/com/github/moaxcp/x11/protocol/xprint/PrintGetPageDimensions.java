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
public class PrintGetPageDimensions implements TwoWayRequest<PrintGetPageDimensionsReply> {
  public static final String PLUGIN_NAME = "xprint";

  public static final byte OPCODE = 21;

  private int context;

  public XReplyFunction<PrintGetPageDimensionsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> PrintGetPageDimensionsReply.readPrintGetPageDimensionsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintGetPageDimensions readPrintGetPageDimensions(X11Input in) throws IOException {
    PrintGetPageDimensions.PrintGetPageDimensionsBuilder javaBuilder = PrintGetPageDimensions.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int context = in.readCard32();
    javaBuilder.context(context);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(context);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintGetPageDimensionsBuilder {
    public int getSize() {
      return 8;
    }
  }
}
