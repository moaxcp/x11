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
public class GetCompressedTexImageARB implements TwoWayRequest<GetCompressedTexImageARBReply>, GlxObject {
  public static final byte OPCODE = (byte) 160;

  private int contextTag;

  private int target;

  private int level;

  public XReplyFunction<GetCompressedTexImageARBReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetCompressedTexImageARBReply.readGetCompressedTexImageARBReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetCompressedTexImageARB readGetCompressedTexImageARB(X11Input in) throws
      IOException {
    GetCompressedTexImageARB.GetCompressedTexImageARBBuilder javaBuilder = GetCompressedTexImageARB.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int target = in.readCard32();
    int level = in.readInt32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.target(target);
    javaBuilder.level(level);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(target);
    out.writeInt32(level);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class GetCompressedTexImageARBBuilder {
    public int getSize() {
      return 16;
    }
  }
}
