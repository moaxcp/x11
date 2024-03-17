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
public class GetPanning implements TwoWayRequest<GetPanningReply> {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 28;

  private int crtc;

  public XReplyFunction<GetPanningReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetPanningReply.readGetPanningReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetPanning readGetPanning(X11Input in) throws IOException {
    GetPanning.GetPanningBuilder javaBuilder = GetPanning.builder();
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

  public static class GetPanningBuilder {
    public int getSize() {
      return 8;
    }
  }
}
