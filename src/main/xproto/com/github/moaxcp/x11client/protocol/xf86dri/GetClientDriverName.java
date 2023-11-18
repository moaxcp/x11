package com.github.moaxcp.x11client.protocol.xf86dri;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetClientDriverName implements TwoWayRequest<GetClientDriverNameReply>, Xf86driObject {
  public static final byte OPCODE = 4;

  private int screen;

  public XReplyFunction<GetClientDriverNameReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetClientDriverNameReply.readGetClientDriverNameReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetClientDriverName readGetClientDriverName(X11Input in) throws IOException {
    GetClientDriverName.GetClientDriverNameBuilder javaBuilder = GetClientDriverName.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int screen = in.readCard32();
    javaBuilder.screen(screen);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(screen);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class GetClientDriverNameBuilder {
    public int getSize() {
      return 8;
    }
  }
}
