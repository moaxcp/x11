package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UseXFont implements OneWayRequest {
  public static final String PLUGIN_NAME = "glx";

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
    byte majorOpcode = in.readCard8();
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
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class UseXFontBuilder {
    public int getSize() {
      return 24;
    }
  }
}
