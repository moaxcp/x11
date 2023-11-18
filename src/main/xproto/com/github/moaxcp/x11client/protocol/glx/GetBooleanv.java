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
public class GetBooleanv implements TwoWayRequest<GetBooleanvReply>, GlxObject {
  public static final byte OPCODE = 112;

  private int contextTag;

  private int pname;

  public XReplyFunction<GetBooleanvReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetBooleanvReply.readGetBooleanvReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetBooleanv readGetBooleanv(X11Input in) throws IOException {
    GetBooleanv.GetBooleanvBuilder javaBuilder = GetBooleanv.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int pname = in.readInt32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.pname(pname);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeInt32(pname);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GetBooleanvBuilder {
    public int getSize() {
      return 12;
    }
  }
}
