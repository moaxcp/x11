package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class PrintGetPrinterListReply implements XReply {
  public static final String PLUGIN_NAME = "xprint";

  private short sequenceNumber;

  @NonNull
  private ImmutableList<Printer> printers;

  public static PrintGetPrinterListReply readPrintGetPrinterListReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    PrintGetPrinterListReply.PrintGetPrinterListReplyBuilder javaBuilder = PrintGetPrinterListReply.builder();
    int length = in.readCard32();
    int listCount = in.readCard32();
    byte[] pad5 = in.readPad(20);
    MutableList<Printer> printers = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(listCount)));
    for(int i = 0; i < Integer.toUnsignedLong(listCount); i++) {
      printers.add(Printer.readPrinter(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.printers(printers.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintGetPrinterListReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(printers);
    }
  }
}
