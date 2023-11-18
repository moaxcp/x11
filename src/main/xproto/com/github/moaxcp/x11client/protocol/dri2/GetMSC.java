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
public class GetMSC implements TwoWayRequest<GetMSCReply>, Dri2Object {
  public static final byte OPCODE = 9;

  private int drawable;

  public XReplyFunction<GetMSCReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetMSCReply.readGetMSCReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetMSC readGetMSC(X11Input in) throws IOException {
    GetMSC.GetMSCBuilder javaBuilder = GetMSC.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int drawable = in.readCard32();
    javaBuilder.drawable(drawable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class GetMSCBuilder {
    public int getSize() {
      return 8;
    }
  }
}
