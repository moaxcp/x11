package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SwapBuffers implements OneWayRequest, GlxObject {
  public static final byte OPCODE = 11;

  private int contextTag;

  private int drawable;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SwapBuffers readSwapBuffers(X11Input in) throws IOException {
    SwapBuffers.SwapBuffersBuilder javaBuilder = SwapBuffers.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int drawable = in.readCard32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.drawable(drawable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(drawable);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class SwapBuffersBuilder {
    public int getSize() {
      return 12;
    }
  }
}
