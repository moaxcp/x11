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
public class GetMapfv implements TwoWayRequest<GetMapfvReply>, GlxObject {
  public static final byte OPCODE = 121;

  private int contextTag;

  private int target;

  private int query;

  public XReplyFunction<GetMapfvReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetMapfvReply.readGetMapfvReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetMapfv readGetMapfv(X11Input in) throws IOException {
    GetMapfv.GetMapfvBuilder javaBuilder = GetMapfv.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int target = in.readCard32();
    int query = in.readCard32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.target(target);
    javaBuilder.query(query);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(target);
    out.writeCard32(query);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class GetMapfvBuilder {
    public int getSize() {
      return 16;
    }
  }
}
