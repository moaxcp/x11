package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class AddGlyphs implements OneWayRequest, RenderObject {
  public static final byte OPCODE = 20;

  private int glyphset;

  @NonNull
  private List<Integer> glyphids;

  @NonNull
  private List<Glyphinfo> glyphs;

  @NonNull
  private List<Byte> data;

  public byte getOpCode() {
    return OPCODE;
  }

  public static AddGlyphs readAddGlyphs(X11Input in) throws IOException {
    AddGlyphs.AddGlyphsBuilder javaBuilder = AddGlyphs.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int glyphset = in.readCard32();
    javaStart += 4;
    int glyphsLen = in.readCard32();
    javaStart += 4;
    List<Integer> glyphids = in.readCard32((int) (Integer.toUnsignedLong(glyphsLen)));
    javaStart += 4 * glyphids.size();
    List<Glyphinfo> glyphs = new ArrayList<>((int) (Integer.toUnsignedLong(glyphsLen)));
    for(int i = 0; i < Integer.toUnsignedLong(glyphsLen); i++) {
      glyphs.add(Glyphinfo.readGlyphinfo(in));
    }
    javaStart += XObject.sizeOf(glyphs);
    List<Byte> data = in.readByte(javaStart - length);
    javaBuilder.glyphset(glyphset);
    javaBuilder.glyphids(glyphids);
    javaBuilder.glyphs(glyphs);
    javaBuilder.data(data);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(glyphset);
    int glyphsLen = glyphs.size();
    out.writeCard32(glyphsLen);
    out.writeCard32(glyphids);
    for(Glyphinfo t : glyphs) {
      t.write(out);
    }
    out.writeByte(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 4 * glyphids.size() + XObject.sizeOf(glyphs) + 1 * data.size();
  }

  public static class AddGlyphsBuilder {
    public int getSize() {
      return 12 + 4 * glyphids.size() + XObject.sizeOf(glyphs) + 1 * data.size();
    }
  }
}
