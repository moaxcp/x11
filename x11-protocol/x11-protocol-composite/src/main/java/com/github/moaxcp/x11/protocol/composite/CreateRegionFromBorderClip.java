package com.github.moaxcp.x11.protocol.composite;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateRegionFromBorderClip implements OneWayRequest {
  public static final String PLUGIN_NAME = "composite";

  public static final byte OPCODE = 5;

  private int region;

  private int window;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateRegionFromBorderClip readCreateRegionFromBorderClip(X11Input in) throws
      IOException {
    CreateRegionFromBorderClip.CreateRegionFromBorderClipBuilder javaBuilder = CreateRegionFromBorderClip.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int region = in.readCard32();
    int window = in.readCard32();
    javaBuilder.region(region);
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(region);
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateRegionFromBorderClipBuilder {
    public int getSize() {
      return 12;
    }
  }
}
