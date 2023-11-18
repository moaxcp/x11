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
public class GetModeLine implements TwoWayRequest<GetModeLineReply>, Xf86vidmodeObject {
  public static final byte OPCODE = 1;

  private short screen;

  public XReplyFunction<GetModeLineReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetModeLineReply.readGetModeLineReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetModeLine readGetModeLine(X11Input in) throws IOException {
    GetModeLine.GetModeLineBuilder javaBuilder = GetModeLine.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short screen = in.readCard16();
    byte[] pad4 = in.readPad(2);
    javaBuilder.screen(screen);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(screen);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class GetModeLineBuilder {
    public int getSize() {
      return 8;
    }
  }
}
