package com.github.moaxcp.x11client.protocol.xproto;

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
public class QueryColors implements TwoWayRequest<QueryColorsReply>, XprotoObject {
  public static final byte OPCODE = 91;

  private int cmap;

  @NonNull
  private List<Integer> pixels;

  public XReplyFunction<QueryColorsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryColorsReply.readQueryColorsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryColors readQueryColors(X11Input in) throws IOException {
    QueryColors.QueryColorsBuilder javaBuilder = QueryColors.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int cmap = in.readCard32();
    javaStart += 4;
    List<Integer> pixels = in.readCard32(javaStart - length);
    javaBuilder.cmap(cmap);
    javaBuilder.pixels(pixels);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
    out.writeCard32(pixels);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 4 * pixels.size();
  }

  public static class QueryColorsBuilder {
    public int getSize() {
      return 8 + 4 * pixels.size();
    }
  }
}
