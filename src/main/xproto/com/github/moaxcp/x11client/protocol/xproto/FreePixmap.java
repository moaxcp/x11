package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FreePixmap implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 54;

  private int pixmap;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreePixmap readFreePixmap(X11Input in) throws IOException {
    FreePixmap.FreePixmapBuilder javaBuilder = FreePixmap.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int pixmap = in.readCard32();
    javaBuilder.pixmap(pixmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(pixmap);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class FreePixmapBuilder {
    public int getSize() {
      return 8;
    }
  }
}
