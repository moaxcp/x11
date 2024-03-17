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
public class PrintGetAttributes implements TwoWayRequest<PrintGetAttributesReply> {
  public static final String PLUGIN_NAME = "xprint";

  public static final byte OPCODE = 17;

  private int context;

  private byte pool;

  public XReplyFunction<PrintGetAttributesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> PrintGetAttributesReply.readPrintGetAttributesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintGetAttributes readPrintGetAttributes(X11Input in) throws IOException {
    PrintGetAttributes.PrintGetAttributesBuilder javaBuilder = PrintGetAttributes.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int context = in.readCard32();
    byte pool = in.readCard8();
    byte[] pad5 = in.readPad(3);
    javaBuilder.context(context);
    javaBuilder.pool(pool);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(context);
    out.writeCard8(pool);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintGetAttributesBuilder {
    public int getSize() {
      return 12;
    }
  }
}
