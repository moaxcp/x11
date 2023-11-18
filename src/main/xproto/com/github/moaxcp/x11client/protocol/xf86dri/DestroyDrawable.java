package com.github.moaxcp.x11client.protocol.xf86dri;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DestroyDrawable implements OneWayRequest, Xf86driObject {
  public static final byte OPCODE = 8;

  private int screen;

  private int drawable;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DestroyDrawable readDestroyDrawable(X11Input in) throws IOException {
    DestroyDrawable.DestroyDrawableBuilder javaBuilder = DestroyDrawable.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int screen = in.readCard32();
    int drawable = in.readCard32();
    javaBuilder.screen(screen);
    javaBuilder.drawable(drawable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(screen);
    out.writeCard32(drawable);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class DestroyDrawableBuilder {
    public int getSize() {
      return 12;
    }
  }
}
