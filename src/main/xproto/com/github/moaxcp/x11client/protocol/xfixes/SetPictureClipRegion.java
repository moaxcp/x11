package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetPictureClipRegion implements OneWayRequest, XfixesObject {
  public static final byte OPCODE = 22;

  private int picture;

  private int region;

  private short xOrigin;

  private short yOrigin;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetPictureClipRegion readSetPictureClipRegion(X11Input in) throws IOException {
    SetPictureClipRegion.SetPictureClipRegionBuilder javaBuilder = SetPictureClipRegion.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int picture = in.readCard32();
    int region = in.readCard32();
    short xOrigin = in.readInt16();
    short yOrigin = in.readInt16();
    javaBuilder.picture(picture);
    javaBuilder.region(region);
    javaBuilder.xOrigin(xOrigin);
    javaBuilder.yOrigin(yOrigin);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
    out.writeCard32(region);
    out.writeInt16(xOrigin);
    out.writeInt16(yOrigin);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class SetPictureClipRegionBuilder {
    public int getSize() {
      return 16;
    }
  }
}
