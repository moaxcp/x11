package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintRehashPrinterList implements OneWayRequest, XprintObject {
  public static final byte OPCODE = 20;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintRehashPrinterList readPrintRehashPrinterList(X11Input in) throws IOException {
    PrintRehashPrinterList.PrintRehashPrinterListBuilder javaBuilder = PrintRehashPrinterList.builder();
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

  public static class PrintRehashPrinterListBuilder {
    public int getSize() {
      return 4;
    }
  }
}
