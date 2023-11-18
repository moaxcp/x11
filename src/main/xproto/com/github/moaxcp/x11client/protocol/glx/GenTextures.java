package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GenTextures implements TwoWayRequest<GenTexturesReply>, GlxObject {
  public static final byte OPCODE = (byte) 145;

  private int contextTag;

  private int n;

  public XReplyFunction<GenTexturesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GenTexturesReply.readGenTexturesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GenTextures readGenTextures(X11Input in) throws IOException {
    GenTextures.GenTexturesBuilder javaBuilder = GenTextures.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int n = in.readInt32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.n(n);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeInt32(n);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GenTexturesBuilder {
    public int getSize() {
      return 12;
    }
  }
}
