package com.github.moaxcp.x11.protocol.xf86vidmode;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetDotClocks implements TwoWayRequest<GetDotClocksReply> {
  public static final String PLUGIN_NAME = "xf86vidmode";

  public static final byte OPCODE = 13;

  private short screen;

  public XReplyFunction<GetDotClocksReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetDotClocksReply.readGetDotClocksReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetDotClocks readGetDotClocks(X11Input in) throws IOException {
    GetDotClocks.GetDotClocksBuilder javaBuilder = GetDotClocks.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short screen = in.readCard16();
    byte[] pad4 = in.readPad(2);
    javaBuilder.screen(screen);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(screen);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDotClocksBuilder {
    public int getSize() {
      return 8;
    }
  }
}
