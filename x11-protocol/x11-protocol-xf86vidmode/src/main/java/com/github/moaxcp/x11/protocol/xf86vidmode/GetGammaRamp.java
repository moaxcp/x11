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
public class GetGammaRamp implements TwoWayRequest<GetGammaRampReply> {
  public static final String PLUGIN_NAME = "xf86vidmode";

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
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short screen = in.readCard16();
    short size = in.readCard16();
    javaBuilder.screen(screen);
    javaBuilder.size(size);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(screen);
    out.writeCard16(size);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetGammaRampBuilder {
    public int getSize() {
      return 8;
    }
  }
}
