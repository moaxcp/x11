package com.github.moaxcp.x11client.protocol.composite;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NameWindowPixmap implements OneWayRequest, CompositeObject {
  public static final byte OPCODE = 6;

  private int window;

  private int pixmap;

  public byte getOpCode() {
    return OPCODE;
  }

  public static NameWindowPixmap readNameWindowPixmap(X11Input in) throws IOException {
    NameWindowPixmap.NameWindowPixmapBuilder javaBuilder = NameWindowPixmap.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    int pixmap = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.pixmap(pixmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(pixmap);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class NameWindowPixmapBuilder {
    public int getSize() {
      return 12;
    }
  }
}
