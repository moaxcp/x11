package com.github.moaxcp.x11.protocol.glx;

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
public class GetSeparableFilterReply implements XReply {
  public static final String PLUGIN_NAME = "glx";

  private short sequenceNumber;

  private int rowW;

  private int colH;

  @NonNull
  private List<Byte> rowsAndCols;

  public static GetSeparableFilterReply readGetSeparableFilterReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetSeparableFilterReply.GetSeparableFilterReplyBuilder javaBuilder = GetSeparableFilterReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(8);
    int rowW = in.readInt32();
    int colH = in.readInt32();
    byte[] pad7 = in.readPad(8);
    List<Byte> rowsAndCols = in.readByte((int) (Integer.toUnsignedLong(length) * 4));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.rowW(rowW);
    javaBuilder.colH(colH);
    javaBuilder.rowsAndCols(rowsAndCols);
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
    int length = rowsAndCols.size();
    out.writeCard32(getLength());
    out.writePad(8);
    out.writeInt32(rowW);
    out.writeInt32(colH);
    out.writePad(8);
    out.writeByte(rowsAndCols);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * rowsAndCols.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetSeparableFilterReplyBuilder {
    public int getSize() {
      return 32 + 1 * rowsAndCols.size();
    }
  }
}
