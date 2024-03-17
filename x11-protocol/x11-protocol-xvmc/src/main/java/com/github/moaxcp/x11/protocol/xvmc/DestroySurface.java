package com.github.moaxcp.x11.protocol.xvmc;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DestroySurface implements OneWayRequest {
  public static final String PLUGIN_NAME = "xvmc";

  public static final byte OPCODE = 5;

  private int surfaceId;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DestroySurface readDestroySurface(X11Input in) throws IOException {
    DestroySurface.DestroySurfaceBuilder javaBuilder = DestroySurface.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int surfaceId = in.readCard32();
    javaBuilder.surfaceId(surfaceId);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(surfaceId);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DestroySurfaceBuilder {
    public int getSize() {
      return 8;
    }
  }
}
