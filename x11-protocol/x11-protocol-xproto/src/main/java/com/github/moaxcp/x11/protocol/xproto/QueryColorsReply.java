package com.github.moaxcp.x11.protocol.xproto;

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
public class QueryColorsReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  @NonNull
  private ImmutableList<Rgb> colors;

  public static QueryColorsReply readQueryColorsReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    QueryColorsReply.QueryColorsReplyBuilder javaBuilder = QueryColorsReply.builder();
    int length = in.readCard32();
    short colorsLen = in.readCard16();
    byte[] pad5 = in.readPad(22);
    MutableList<Rgb> colors = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(colorsLen));
    for(int i = 0; i < Short.toUnsignedInt(colorsLen); i++) {
      colors.add(Rgb.readRgb(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.colors(colors.toImmutable());
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryColorsReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(colors);
    }
  }
}
