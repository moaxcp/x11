package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PrintGetPrinterList implements TwoWayRequest<PrintGetPrinterListReply>, XprintObject {
  public static final byte OPCODE = 1;

  @NonNull
  private List<Byte> printerName;

  @NonNull
  private List<Byte> locale;

  public XReplyFunction<PrintGetPrinterListReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> PrintGetPrinterListReply.readPrintGetPrinterListReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintGetPrinterList readPrintGetPrinterList(X11Input in) throws IOException {
    PrintGetPrinterList.PrintGetPrinterListBuilder javaBuilder = PrintGetPrinterList.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int printerNameLen = in.readCard32();
    int localeLen = in.readCard32();
    List<Byte> printerName = in.readChar((int) (Integer.toUnsignedLong(printerNameLen)));
    List<Byte> locale = in.readChar((int) (Integer.toUnsignedLong(localeLen)));
    javaBuilder.printerName(printerName);
    javaBuilder.locale(locale);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    int printerNameLen = printerName.size();
    out.writeCard32(printerNameLen);
    int localeLen = locale.size();
    out.writeCard32(localeLen);
    out.writeChar(printerName);
    out.writeChar(locale);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 1 * printerName.size() + 1 * locale.size();
  }

  public static class PrintGetPrinterListBuilder {
    public int getSize() {
      return 12 + 1 * printerName.size() + 1 * locale.size();
    }
  }
}
