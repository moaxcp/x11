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
public class PolyText16 implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 75;

  private int drawable;

  private int gc;

  private short x;

  private short y;

  @NonNull
  private List<Byte> items;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PolyText16 readPolyText16(X11Input in) throws IOException {
    PolyText16.PolyText16Builder javaBuilder = PolyText16.builder();
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
    List<Byte> items = in.readByte(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.items(items);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PolyText16Builder {
    public int getSize() {
      return 16 + 1 * items.size();
    }
  }
}
