package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PrintGetDocumentDataReply implements XReply {
  public static final String PLUGIN_NAME = "xprint";

  private short sequenceNumber;

  private int statusCode;

  private int finishedFlag;

  @NonNull
  private List<Byte> data;

  public static PrintGetDocumentDataReply readPrintGetDocumentDataReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    PrintGetDocumentDataReply.PrintGetDocumentDataReplyBuilder javaBuilder = PrintGetDocumentDataReply.builder();
    int length = in.readCard32();
    int statusCode = in.readCard32();
    int finishedFlag = in.readCard32();
    int dataLen = in.readCard32();
    byte[] pad7 = in.readPad(12);
    List<Byte> data = in.readByte((int) (Integer.toUnsignedLong(dataLen)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.statusCode(statusCode);
    javaBuilder.finishedFlag(finishedFlag);
    javaBuilder.data(data);
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
    out.writeCard32(statusCode);
    out.writeCard32(finishedFlag);
    int dataLen = data.size();
    out.writeCard32(dataLen);
    out.writePad(12);
    out.writeByte(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * data.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrintGetDocumentDataReplyBuilder {
    public int getSize() {
      return 32 + 1 * data.size();
    }
  }
}
