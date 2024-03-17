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
public class PrintGetContext implements TwoWayRequest<PrintGetContextReply> {
  public static final String PLUGIN_NAME = "xprint";

  public static final byte OPCODE = 4;

  public XReplyFunction<PrintGetContextReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> PrintGetContextReply.readPrintGetContextReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintGetContext readPrintGetContext(X11Input in) throws IOException {
    PrintGetContext.PrintGetContextBuilder javaBuilder = PrintGetContext.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintGetContextBuilder {
    public int getSize() {
      return 4;
    }
  }
}
