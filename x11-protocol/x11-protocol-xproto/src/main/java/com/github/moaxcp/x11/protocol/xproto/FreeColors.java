package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class FreeColors implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 88;

  private int cmap;

  private int planeMask;

  @NonNull
  private IntList pixels;

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
    IntList pixels = in.readCard32(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.cmap(cmap);
    javaBuilder.planeMask(planeMask);
    javaBuilder.pixels(pixels.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class FreeColorsBuilder {
    public int getSize() {
      return 12 + 4 * pixels.size();
    }
  }
}
