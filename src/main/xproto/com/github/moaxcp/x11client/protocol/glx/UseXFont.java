package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UseXFont implements OneWayRequest, GlxObject {
  public static final byte OPCODE = 12;

  private int contextTag;

  private int font;

  private int first;

  private int count;

  private int listBase;

  public byte getOpCode() {
    return OPCODE;
  }

  public static UseXFont readUseXFont(X11Input in) throws IOException {
    UseXFont.UseXFontBuilder javaBuilder = UseXFont.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int font = in.readCard32();
    int first = in.readCard32();
    int count = in.readCard32();
    int listBase = in.readCard32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.font(font);
    javaBuilder.first(first);
    javaBuilder.count(count);
    javaBuilder.listBase(listBase);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(font);
    out.writeCard32(first);
    out.writeCard32(count);
    out.writeCard32(listBase);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public static class UseXFontBuilder {
    public int getSize() {
      return 24;
    }
  }
}
