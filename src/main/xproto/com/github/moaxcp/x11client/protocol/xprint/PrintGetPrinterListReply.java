package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PrintGetPrinterListReply implements XReply, XprintObject {
  private short sequenceNumber;

  @NonNull
  private List<Printer> printers;

  public static PrintGetPrinterListReply readPrintGetPrinterListReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    PrintGetPrinterListReply.PrintGetPrinterListReplyBuilder javaBuilder = PrintGetPrinterListReply.builder();
    int length = in.readCard32();
    int listCount = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<Printer> printers = new ArrayList<>((int) (Integer.toUnsignedLong(listCount)));
    for(int i = 0; i < Integer.toUnsignedLong(listCount); i++) {
      printers.add(Printer.readPrinter(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.printers(printers);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    int listCount = printers.size();
    out.writeCard32(listCount);
    out.writePad(20);
    for(Printer t : printers) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(printers);
  }

  public static class PrintGetPrinterListReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(printers);
    }
  }
}
