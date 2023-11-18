package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateGlyphCursor implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 94;

  private int cid;

  private int sourceFont;

  private int maskFont;

  private short sourceChar;

  private short maskChar;

  private short foreRed;

  private short foreGreen;

  private short foreBlue;

  private short backRed;

  private short backGreen;

  private short backBlue;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateGlyphCursor readCreateGlyphCursor(X11Input in) throws IOException {
    CreateGlyphCursor.CreateGlyphCursorBuilder javaBuilder = CreateGlyphCursor.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cid = in.readCard32();
    int sourceFont = in.readCard32();
    int maskFont = in.readCard32();
    short sourceChar = in.readCard16();
    short maskChar = in.readCard16();
    short foreRed = in.readCard16();
    short foreGreen = in.readCard16();
    short foreBlue = in.readCard16();
    short backRed = in.readCard16();
    short backGreen = in.readCard16();
    short backBlue = in.readCard16();
    javaBuilder.cid(cid);
    javaBuilder.sourceFont(sourceFont);
    javaBuilder.maskFont(maskFont);
    javaBuilder.sourceChar(sourceChar);
    javaBuilder.maskChar(maskChar);
    javaBuilder.foreRed(foreRed);
    javaBuilder.foreGreen(foreGreen);
    javaBuilder.foreBlue(foreBlue);
    javaBuilder.backRed(backRed);
    javaBuilder.backGreen(backGreen);
    javaBuilder.backBlue(backBlue);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cid);
    out.writeCard32(sourceFont);
    out.writeCard32(maskFont);
    out.writeCard16(sourceChar);
    out.writeCard16(maskChar);
    out.writeCard16(foreRed);
    out.writeCard16(foreGreen);
    out.writeCard16(foreBlue);
    out.writeCard16(backRed);
    out.writeCard16(backGreen);
    out.writeCard16(backBlue);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class CreateGlyphCursorBuilder {
    public int getSize() {
      return 32;
    }
  }
}
