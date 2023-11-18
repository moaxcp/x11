package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CopyContext implements OneWayRequest, GlxObject {
  public static final byte OPCODE = 10;

  private int src;

  private int dest;

  private int mask;

  private int srcContextTag;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CopyContext readCopyContext(X11Input in) throws IOException {
    CopyContext.CopyContextBuilder javaBuilder = CopyContext.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int src = in.readCard32();
    int dest = in.readCard32();
    int mask = in.readCard32();
    int srcContextTag = in.readCard32();
    javaBuilder.src(src);
    javaBuilder.dest(dest);
    javaBuilder.mask(mask);
    javaBuilder.srcContextTag(srcContextTag);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(src);
    out.writeCard32(dest);
    out.writeCard32(mask);
    out.writeCard32(srcContextTag);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public static class CopyContextBuilder {
    public int getSize() {
      return 20;
    }
  }
}
