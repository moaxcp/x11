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
public class FreeColors implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 88;

  private int cmap;

  private int planeMask;

  @NonNull
  private List<Integer> pixels;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreeColors readFreeColors(X11Input in) throws IOException {
    FreeColors.FreeColorsBuilder javaBuilder = FreeColors.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int cmap = in.readCard32();
    javaStart += 4;
    int planeMask = in.readCard32();
    javaStart += 4;
    List<Integer> pixels = in.readCard32(javaStart - length);
    javaBuilder.cmap(cmap);
    javaBuilder.planeMask(planeMask);
    javaBuilder.pixels(pixels);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
    out.writeCard32(planeMask);
    out.writeCard32(pixels);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 4 * pixels.size();
  }

  public static class FreeColorsBuilder {
    public int getSize() {
      return 12 + 4 * pixels.size();
    }
  }
}
