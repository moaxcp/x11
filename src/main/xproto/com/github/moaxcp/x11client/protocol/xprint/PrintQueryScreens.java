package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintQueryScreens implements TwoWayRequest<PrintQueryScreensReply>, XprintObject {
  public static final byte OPCODE = 22;

  public XReplyFunction<PrintQueryScreensReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> PrintQueryScreensReply.readPrintQueryScreensReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintQueryScreens readPrintQueryScreens(X11Input in) throws IOException {
    PrintQueryScreens.PrintQueryScreensBuilder javaBuilder = PrintQueryScreens.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public static class PrintQueryScreensBuilder {
    public int getSize() {
      return 4;
    }
  }
}
