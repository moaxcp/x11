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
public class PrintGetDocumentData implements TwoWayRequest<PrintGetDocumentDataReply> {
  public static final String PLUGIN_NAME = "xprint";

  public static final byte OPCODE = 12;

  private int context;

  private int maxBytes;

  public XReplyFunction<PrintGetDocumentDataReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> PrintGetDocumentDataReply.readPrintGetDocumentDataReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintGetDocumentData readPrintGetDocumentData(X11Input in) throws IOException {
    PrintGetDocumentData.PrintGetDocumentDataBuilder javaBuilder = PrintGetDocumentData.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int context = in.readCard32();
    int maxBytes = in.readCard32();
    javaBuilder.context(context);
    javaBuilder.maxBytes(maxBytes);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(context);
    out.writeCard32(maxBytes);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintGetDocumentDataBuilder {
    public int getSize() {
      return 12;
    }
  }
}
