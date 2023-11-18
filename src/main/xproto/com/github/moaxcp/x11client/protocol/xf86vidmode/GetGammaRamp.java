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
public class GetGammaRamp implements TwoWayRequest<GetGammaRampReply>, Xf86vidmodeObject {
  public static final byte OPCODE = 17;

  private short screen;

  private short size;

  public XReplyFunction<GetGammaRampReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetGammaRampReply.readGetGammaRampReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetGammaRamp readGetGammaRamp(X11Input in) throws IOException {
    GetGammaRamp.GetGammaRampBuilder javaBuilder = GetGammaRamp.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short screen = in.readCard16();
    short size = in.readCard16();
    javaBuilder.screen(screen);
    javaBuilder.size(size);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(screen);
    out.writeCard16(size);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class GetGammaRampBuilder {
    public int getSize() {
      return 8;
    }
  }
}
