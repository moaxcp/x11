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
public class GetPropertyCreateContext implements TwoWayRequest<GetPropertyCreateContextReply>, XselinuxObject {
  public static final byte OPCODE = 9;

  public XReplyFunction<GetPropertyCreateContextReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetPropertyCreateContextReply.readGetPropertyCreateContextReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetPropertyCreateContext readGetPropertyCreateContext(X11Input in) throws
      IOException {
    GetPropertyCreateContext.GetPropertyCreateContextBuilder javaBuilder = GetPropertyCreateContext.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public static class GetPropertyCreateContextBuilder {
    public int getSize() {
      return 4;
    }
  }
}
