package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FreePicture implements OneWayRequest, RenderObject {
  public static final byte OPCODE = 7;

  private int picture;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreePicture readFreePicture(X11Input in) throws IOException {
    FreePicture.FreePictureBuilder javaBuilder = FreePicture.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int picture = in.readCard32();
    javaBuilder.picture(picture);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class FreePictureBuilder {
    public int getSize() {
      return 8;
    }
  }
}
