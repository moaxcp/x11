package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class ImageText16 implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 77;

  private int drawable;

  private int gc;

  private short x;

  private short y;

  @NonNull
  private ImmutableList<Char2b> string;

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
    MutableList<Char2b> string = Lists.mutable.withInitialCapacity(stringLen);
    for(int i = 0; i < stringLen; i++) {
      string.add(Char2b.readChar2b(in));
    }
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.string(string.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ImageText16Builder {
    public int getSize() {
      return 16 + XObject.sizeOf(string);
    }
  }
}
