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
public class GetQueryObjectuivARB implements TwoWayRequest<GetQueryObjectuivARBReply>, GlxObject {
  public static final byte OPCODE = (byte) 166;

  private int contextTag;

  private int id;

  private int pname;

  public XReplyFunction<GetQueryObjectuivARBReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetQueryObjectuivARBReply.readGetQueryObjectuivARBReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetQueryObjectuivARB readGetQueryObjectuivARB(X11Input in) throws IOException {
    GetQueryObjectuivARB.GetQueryObjectuivARBBuilder javaBuilder = GetQueryObjectuivARB.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int id = in.readCard32();
    int pname = in.readCard32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.id(id);
    javaBuilder.pname(pname);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(id);
    out.writeCard32(pname);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class GetQueryObjectuivARBBuilder {
    public int getSize() {
      return 16;
    }
  }
}
