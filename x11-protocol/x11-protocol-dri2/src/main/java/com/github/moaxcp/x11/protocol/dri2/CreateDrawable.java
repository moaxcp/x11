package com.github.moaxcp.x11.protocol.dri2;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateDrawable implements OneWayRequest {
  public static final String PLUGIN_NAME = "dri2";

  public static final byte OPCODE = 3;

  private int drawable;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateDrawable readCreateDrawable(X11Input in) throws IOException {
    CreateDrawable.CreateDrawableBuilder javaBuilder = CreateDrawable.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    javaBuilder.drawable(drawable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateDrawableBuilder {
    public int getSize() {
      return 8;
    }
  }
}
