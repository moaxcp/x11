package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateRegionFromPicture implements OneWayRequest, XfixesObject {
  public static final byte OPCODE = 9;

  private int region;

  private int picture;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateRegionFromPicture readCreateRegionFromPicture(X11Input in) throws
      IOException {
    CreateRegionFromPicture.CreateRegionFromPictureBuilder javaBuilder = CreateRegionFromPicture.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int region = in.readCard32();
    int picture = in.readCard32();
    javaBuilder.region(region);
    javaBuilder.picture(picture);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(region);
    out.writeCard32(picture);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class CreateRegionFromPictureBuilder {
    public int getSize() {
      return 12;
    }
  }
}
