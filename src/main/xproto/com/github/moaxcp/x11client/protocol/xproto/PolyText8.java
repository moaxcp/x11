package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PolyText8 implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 74;

  private int drawable;

  private int gc;

  private short x;

  private short y;

  @NonNull
  private List<Byte> items;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PolyText8 readPolyText8(X11Input in) throws IOException {
    PolyText8.PolyText8Builder javaBuilder = PolyText8.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int drawable = in.readCard32();
    javaStart += 4;
    int gc = in.readCard32();
    javaStart += 4;
    short x = in.readInt16();
    javaStart += 2;
    short y = in.readInt16();
    javaStart += 2;
    List<Byte> items = in.readByte(javaStart - length);
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.items(items);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(gc);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeByte(items);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + 1 * items.size();
  }

  public static class PolyText8Builder {
    public int getSize() {
      return 16 + 1 * items.size();
    }
  }
}
