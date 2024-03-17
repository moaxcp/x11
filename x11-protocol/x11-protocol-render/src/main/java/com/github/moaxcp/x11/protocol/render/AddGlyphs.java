package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class AddGlyphs implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";

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
    byte majorOpcode = in.readCard8();
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
    List<Byte> data = in.readByte(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.glyphset(glyphset);
    javaBuilder.glyphids(glyphids);
    javaBuilder.glyphs(glyphs);
    javaBuilder.data(data);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class AddGlyphsBuilder {
    public int getSize() {
      return 12 + 4 * glyphids.size() + XObject.sizeOf(glyphs) + 1 * data.size();
    }
  }
}
