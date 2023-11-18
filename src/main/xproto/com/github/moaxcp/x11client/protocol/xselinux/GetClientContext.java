package com.github.moaxcp.x11client.protocol.xselinux;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetClientContext implements TwoWayRequest<GetClientContextReply>, XselinuxObject {
  public static final byte OPCODE = 22;

  private int resource;

  public XReplyFunction<GetClientContextReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetClientContextReply.readGetClientContextReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetClientContext readGetClientContext(X11Input in) throws IOException {
    GetClientContext.GetClientContextBuilder javaBuilder = GetClientContext.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int resource = in.readCard32();
    javaBuilder.resource(resource);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(resource);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class GetClientContextBuilder {
    public int getSize() {
      return 8;
    }
  }
}
