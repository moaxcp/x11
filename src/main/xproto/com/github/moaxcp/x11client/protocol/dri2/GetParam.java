package com.github.moaxcp.x11client.protocol.dri2;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetParam implements TwoWayRequest<GetParamReply>, Dri2Object {
  public static final byte OPCODE = 13;

  private int drawable;

  private int param;

  public XReplyFunction<GetParamReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetParamReply.readGetParamReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetParam readGetParam(X11Input in) throws IOException {
    GetParam.GetParamBuilder javaBuilder = GetParam.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int drawable = in.readCard32();
    int param = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.param(param);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(param);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GetParamBuilder {
    public int getSize() {
      return 12;
    }
  }
}
