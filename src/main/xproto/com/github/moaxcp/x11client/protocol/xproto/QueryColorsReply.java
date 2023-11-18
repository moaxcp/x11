package com.github.moaxcp.x11client.protocol.xproto;

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
public class QueryColorsReply implements XReply, XprotoObject {
  private short sequenceNumber;

  @NonNull
  private List<Rgb> colors;

  public static QueryColorsReply readQueryColorsReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    QueryColorsReply.QueryColorsReplyBuilder javaBuilder = QueryColorsReply.builder();
    int length = in.readCard32();
    short colorsLen = in.readCard16();
    byte[] pad5 = in.readPad(22);
    List<Rgb> colors = new ArrayList<>(Short.toUnsignedInt(colorsLen));
    for(int i = 0; i < Short.toUnsignedInt(colorsLen); i++) {
      colors.add(Rgb.readRgb(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.colors(colors);
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
    short colorsLen = (short) colors.size();
    out.writeCard16(colorsLen);
    out.writePad(22);
    for(Rgb t : colors) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(colors);
  }

  public static class QueryColorsReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(colors);
    }
  }
}
