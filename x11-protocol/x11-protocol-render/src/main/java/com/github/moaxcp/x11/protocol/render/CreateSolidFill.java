package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreateSolidFill implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";

  public static final byte OPCODE = 33;

  private int picture;

  @NonNull
  private Color color;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateSolidFill readCreateSolidFill(X11Input in) throws IOException {
    CreateSolidFill.CreateSolidFillBuilder javaBuilder = CreateSolidFill.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int picture = in.readCard32();
    Color color = Color.readColor(in);
    javaBuilder.picture(picture);
    javaBuilder.color(color);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
    color.write(out);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateSolidFillBuilder {
    public int getSize() {
      return 16;
    }
  }
}
