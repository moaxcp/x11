package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateRegionFromBitmap implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";

  public static final byte OPCODE = 6;

  private int region;

  private int bitmap;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateRegionFromBitmap readCreateRegionFromBitmap(X11Input in) throws IOException {
    CreateRegionFromBitmap.CreateRegionFromBitmapBuilder javaBuilder = CreateRegionFromBitmap.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int region = in.readCard32();
    int bitmap = in.readCard32();
    javaBuilder.region(region);
    javaBuilder.bitmap(bitmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(region);
    out.writeCard32(bitmap);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateRegionFromBitmapBuilder {
    public int getSize() {
      return 12;
    }
  }
}
