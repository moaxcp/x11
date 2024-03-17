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
public class PrintGetScreenOfContext implements TwoWayRequest<PrintGetScreenOfContextReply> {
  public static final String PLUGIN_NAME = "xprint";

  public static final byte OPCODE = 6;

  public XReplyFunction<PrintGetScreenOfContextReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> PrintGetScreenOfContextReply.readPrintGetScreenOfContextReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintGetScreenOfContext readPrintGetScreenOfContext(X11Input in) throws
      IOException {
    PrintGetScreenOfContext.PrintGetScreenOfContextBuilder javaBuilder = PrintGetScreenOfContext.builder();
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

  public static class PrintGetScreenOfContextBuilder {
    public int getSize() {
      return 4;
    }
  }
}
