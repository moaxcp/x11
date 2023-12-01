package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PrintPutDocumentData implements OneWayRequest, XprintObject {
  public static final byte OPCODE = 11;

  private int drawable;

  @NonNull
  private List<Byte> data;

  @NonNull
  private List<Byte> docFormat;

  @NonNull
  private List<Byte> options;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintPutDocumentData readPrintPutDocumentData(X11Input in) throws IOException {
    PrintPutDocumentData.PrintPutDocumentDataBuilder javaBuilder = PrintPutDocumentData.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int drawable = in.readCard32();
    int lenData = in.readCard32();
    short lenFmt = in.readCard16();
    short lenOptions = in.readCard16();
    List<Byte> data = in.readByte((int) (Integer.toUnsignedLong(lenData)));
    List<Byte> docFormat = in.readChar(Short.toUnsignedInt(lenFmt));
    List<Byte> options = in.readChar(Short.toUnsignedInt(lenOptions));
    javaBuilder.drawable(drawable);
    javaBuilder.data(data);
    javaBuilder.docFormat(docFormat);
    javaBuilder.options(options);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    int lenData = data.size();
    out.writeCard32(lenData);
    short lenFmt = (short) docFormat.size();
    out.writeCard16(lenFmt);
    short lenOptions = (short) options.size();
    out.writeCard16(lenOptions);
    out.writeByte(data);
    out.writeChar(docFormat);
    out.writeChar(options);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + 1 * data.size() + 1 * docFormat.size() + 1 * options.size();
  }

  public static class PrintPutDocumentDataBuilder {
    public int getSize() {
      return 16 + 1 * data.size() + 1 * docFormat.size() + 1 * options.size();
    }
  }
}
