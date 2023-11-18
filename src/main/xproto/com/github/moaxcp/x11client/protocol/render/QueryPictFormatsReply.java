package com.github.moaxcp.x11client.protocol.render;

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
public class QueryPictFormatsReply implements XReply, RenderObject {
  private short sequenceNumber;

  private int numDepths;

  private int numVisuals;

  @NonNull
  private List<Pictforminfo> formats;

  @NonNull
  private List<Pictscreen> screens;

  @NonNull
  private List<Integer> subpixels;

  public static QueryPictFormatsReply readQueryPictFormatsReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryPictFormatsReply.QueryPictFormatsReplyBuilder javaBuilder = QueryPictFormatsReply.builder();
    int length = in.readCard32();
    int numFormats = in.readCard32();
    int numScreens = in.readCard32();
    int numDepths = in.readCard32();
    int numVisuals = in.readCard32();
    int numSubpixel = in.readCard32();
    byte[] pad9 = in.readPad(4);
    List<Pictforminfo> formats = new ArrayList<>((int) (Integer.toUnsignedLong(numFormats)));
    for(int i = 0; i < Integer.toUnsignedLong(numFormats); i++) {
      formats.add(Pictforminfo.readPictforminfo(in));
    }
    List<Pictscreen> screens = new ArrayList<>((int) (Integer.toUnsignedLong(numScreens)));
    for(int i = 0; i < Integer.toUnsignedLong(numScreens); i++) {
      screens.add(Pictscreen.readPictscreen(in));
    }
    List<Integer> subpixels = in.readCard32((int) (Integer.toUnsignedLong(numSubpixel)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.numDepths(numDepths);
    javaBuilder.numVisuals(numVisuals);
    javaBuilder.formats(formats);
    javaBuilder.screens(screens);
    javaBuilder.subpixels(subpixels);
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
    int numFormats = formats.size();
    out.writeCard32(numFormats);
    int numScreens = screens.size();
    out.writeCard32(numScreens);
    out.writeCard32(numDepths);
    out.writeCard32(numVisuals);
    int numSubpixel = subpixels.size();
    out.writeCard32(numSubpixel);
    out.writePad(4);
    for(Pictforminfo t : formats) {
      t.write(out);
    }
    for(Pictscreen t : screens) {
      t.write(out);
    }
    out.writeCard32(subpixels);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(formats) + XObject.sizeOf(screens) + 4 * subpixels.size();
  }

  public static class QueryPictFormatsReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(formats) + XObject.sizeOf(screens) + 4 * subpixels.size();
    }
  }
}
