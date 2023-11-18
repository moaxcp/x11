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
public class IsTexture implements TwoWayRequest<IsTextureReply>, GlxObject {
  public static final byte OPCODE = (byte) 146;

  private int contextTag;

  private int texture;

  public XReplyFunction<IsTextureReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> IsTextureReply.readIsTextureReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static IsTexture readIsTexture(X11Input in) throws IOException {
    IsTexture.IsTextureBuilder javaBuilder = IsTexture.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int texture = in.readCard32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.texture(texture);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(texture);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class IsTextureBuilder {
    public int getSize() {
      return 12;
    }
  }
}
