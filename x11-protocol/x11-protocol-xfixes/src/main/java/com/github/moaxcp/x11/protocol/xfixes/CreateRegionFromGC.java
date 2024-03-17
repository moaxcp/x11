package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateRegionFromGC implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";

  public static final byte OPCODE = 8;

  private int region;

  private int gc;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateRegionFromGC readCreateRegionFromGC(X11Input in) throws IOException {
    CreateRegionFromGC.CreateRegionFromGCBuilder javaBuilder = CreateRegionFromGC.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int region = in.readCard32();
    int gc = in.readCard32();
    javaBuilder.region(region);
    javaBuilder.gc(gc);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(region);
    out.writeCard32(gc);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateRegionFromGCBuilder {
    public int getSize() {
      return 12;
    }
  }
}
