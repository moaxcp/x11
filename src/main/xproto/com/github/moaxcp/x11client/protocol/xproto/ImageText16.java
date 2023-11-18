package com.github.moaxcp.x11client.protocol.xproto;

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
public class ImageText16 implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 77;

  private int drawable;

  private int gc;

  private short x;

  private short y;

  @NonNull
  private List<Char2b> string;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ImageText16 readImageText16(X11Input in) throws IOException {
    ImageText16.ImageText16Builder javaBuilder = ImageText16.builder();
    byte stringLen = in.readByte();
    short length = in.readCard16();
    int drawable = in.readCard32();
    int gc = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    List<Char2b> string = new ArrayList<>(stringLen);
    for(int i = 0; i < stringLen; i++) {
      string.add(Char2b.readChar2b(in));
    }
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.string(string);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    byte stringLen = (byte) string.size();
    out.writeByte(stringLen);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(gc);
    out.writeInt16(x);
    out.writeInt16(y);
    for(Char2b t : string) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + XObject.sizeOf(string);
  }

  public static class ImageText16Builder {
    public int getSize() {
      return 16 + XObject.sizeOf(string);
    }
  }
}
