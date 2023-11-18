package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DestroyPbuffer implements OneWayRequest, GlxObject {
  public static final byte OPCODE = 28;

  private int pbuffer;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DestroyPbuffer readDestroyPbuffer(X11Input in) throws IOException {
    DestroyPbuffer.DestroyPbufferBuilder javaBuilder = DestroyPbuffer.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int pbuffer = in.readCard32();
    javaBuilder.pbuffer(pbuffer);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(pbuffer);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class DestroyPbufferBuilder {
    public int getSize() {
      return 8;
    }
  }
}
