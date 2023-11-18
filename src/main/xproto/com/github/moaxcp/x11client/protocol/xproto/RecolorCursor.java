package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RecolorCursor implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 96;

  private int cursor;

  private short foreRed;

  private short foreGreen;

  private short foreBlue;

  private short backRed;

  private short backGreen;

  private short backBlue;

  public byte getOpCode() {
    return OPCODE;
  }

  public static RecolorCursor readRecolorCursor(X11Input in) throws IOException {
    RecolorCursor.RecolorCursorBuilder javaBuilder = RecolorCursor.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cursor = in.readCard32();
    short foreRed = in.readCard16();
    short foreGreen = in.readCard16();
    short foreBlue = in.readCard16();
    short backRed = in.readCard16();
    short backGreen = in.readCard16();
    short backBlue = in.readCard16();
    javaBuilder.cursor(cursor);
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
    out.writeCard32(cursor);
    out.writeCard16(foreRed);
    out.writeCard16(foreGreen);
    out.writeCard16(foreBlue);
    out.writeCard16(backRed);
    out.writeCard16(backGreen);
    out.writeCard16(backBlue);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public static class RecolorCursorBuilder {
    public int getSize() {
      return 20;
    }
  }
}
