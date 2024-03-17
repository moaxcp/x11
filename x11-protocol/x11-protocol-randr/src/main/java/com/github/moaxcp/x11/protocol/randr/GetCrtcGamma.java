package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetCrtcGamma implements TwoWayRequest<GetCrtcGammaReply> {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 23;

  private int crtc;

  public XReplyFunction<GetCrtcGammaReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetCrtcGammaReply.readGetCrtcGammaReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetCrtcGamma readGetCrtcGamma(X11Input in) throws IOException {
    GetCrtcGamma.GetCrtcGammaBuilder javaBuilder = GetCrtcGamma.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int crtc = in.readCard32();
    javaBuilder.crtc(crtc);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(crtc);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetCrtcGammaBuilder {
    public int getSize() {
      return 8;
    }
  }
}
