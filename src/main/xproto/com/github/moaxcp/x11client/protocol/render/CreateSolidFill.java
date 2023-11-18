package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreateSolidFill implements OneWayRequest, RenderObject {
  public static final byte OPCODE = 33;

  private int picture;

  @NonNull
  private Color color;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateSolidFill readCreateSolidFill(X11Input in) throws IOException {
    CreateSolidFill.CreateSolidFillBuilder javaBuilder = CreateSolidFill.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int picture = in.readCard32();
    Color color = Color.readColor(in);
    javaBuilder.picture(picture);
    javaBuilder.color(color);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
    color.write(out);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class CreateSolidFillBuilder {
    public int getSize() {
      return 16;
    }
  }
}
