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
public class GetWindowContext implements TwoWayRequest<GetWindowContextReply>, XselinuxObject {
  public static final byte OPCODE = 7;

  private int window;

  public XReplyFunction<GetWindowContextReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetWindowContextReply.readGetWindowContextReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetWindowContext readGetWindowContext(X11Input in) throws IOException {
    GetWindowContext.GetWindowContextBuilder javaBuilder = GetWindowContext.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class GetWindowContextBuilder {
    public int getSize() {
      return 8;
    }
  }
}
