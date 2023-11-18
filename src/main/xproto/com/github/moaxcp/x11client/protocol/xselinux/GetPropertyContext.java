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
public class GetPropertyContext implements TwoWayRequest<GetPropertyContextReply>, XselinuxObject {
  public static final byte OPCODE = 12;

  private int window;

  private int property;

  public XReplyFunction<GetPropertyContextReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetPropertyContextReply.readGetPropertyContextReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetPropertyContext readGetPropertyContext(X11Input in) throws IOException {
    GetPropertyContext.GetPropertyContextBuilder javaBuilder = GetPropertyContext.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    int property = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.property(property);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(property);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GetPropertyContextBuilder {
    public int getSize() {
      return 12;
    }
  }
}
