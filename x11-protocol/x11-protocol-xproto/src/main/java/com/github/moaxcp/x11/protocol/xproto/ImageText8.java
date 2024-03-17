package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ImageText8 implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 76;

  private int drawable;

  private int gc;

  private short x;

  private short y;

  @NonNull
  private List<Byte> string;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ImageText8 readImageText8(X11Input in) throws IOException {
    ImageText8.ImageText8Builder javaBuilder = ImageText8.builder();
    byte stringLen = in.readByte();
    short length = in.readCard16();
    int drawable = in.readCard32();
    int gc = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    List<Byte> string = in.readChar(stringLen);
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.string(string);
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
    out.writeChar(string);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + 1 * string.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ImageText8Builder {
    public int getSize() {
      return 16 + 1 * string.size();
    }
  }
}
