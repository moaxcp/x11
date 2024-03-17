package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TranslateRegion implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";

  public static final byte OPCODE = 17;

  private int region;

  private short dx;

  private short dy;

  public byte getOpCode() {
    return OPCODE;
  }

  public static TranslateRegion readTranslateRegion(X11Input in) throws IOException {
    TranslateRegion.TranslateRegionBuilder javaBuilder = TranslateRegion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int region = in.readCard32();
    short dx = in.readInt16();
    short dy = in.readInt16();
    javaBuilder.region(region);
    javaBuilder.dx(dx);
    javaBuilder.dy(dy);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(region);
    out.writeInt16(dx);
    out.writeInt16(dy);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class TranslateRegionBuilder {
    public int getSize() {
      return 12;
    }
  }
}
