package com.github.moaxcp.x11client.protocol.xf86vidmode;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetGamma implements TwoWayRequest<GetGammaReply>, Xf86vidmodeObject {
  public static final byte OPCODE = 16;

  private short screen;

  public XReplyFunction<GetGammaReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetGammaReply.readGetGammaReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetGamma readGetGamma(X11Input in) throws IOException {
    GetGamma.GetGammaBuilder javaBuilder = GetGamma.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short screen = in.readCard16();
    byte[] pad4 = in.readPad(26);
    javaBuilder.screen(screen);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(screen);
    out.writePad(26);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class GetGammaBuilder {
    public int getSize() {
      return 32;
    }
  }
}
