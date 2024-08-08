package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class PrintPutDocumentData implements OneWayRequest {
  public static final String PLUGIN_NAME = "xprint";

  public static final byte OPCODE = 11;

  private int drawable;

  @NonNull
  private ByteList data;

  @NonNull
  private ByteList docFormat;

  @NonNull
  private ByteList options;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PrintPutDocumentData readPrintPutDocumentData(X11Input in) throws IOException {
    PrintPutDocumentData.PrintPutDocumentDataBuilder javaBuilder = PrintPutDocumentData.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    int lenData = in.readCard32();
    short lenFmt = in.readCard16();
    short lenOptions = in.readCard16();
    ByteList data = in.readByte((int) (Integer.toUnsignedLong(lenData)));
    ByteList docFormat = in.readChar(Short.toUnsignedInt(lenFmt));
    ByteList options = in.readChar(Short.toUnsignedInt(lenOptions));
    javaBuilder.drawable(drawable);
    javaBuilder.data(data.toImmutable());
    javaBuilder.docFormat(docFormat.toImmutable());
    javaBuilder.options(options.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintPutDocumentDataBuilder {
    public int getSize() {
      return 16 + 1 * data.size() + 1 * docFormat.size() + 1 * options.size();
    }
  }
}
