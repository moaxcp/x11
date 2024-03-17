package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetIndicatorState implements TwoWayRequest<GetIndicatorStateReply> {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte OPCODE = 12;

  private short deviceSpec;

  public XReplyFunction<GetIndicatorStateReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetIndicatorStateReply.readGetIndicatorStateReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetIndicatorState readGetIndicatorState(X11Input in) throws IOException {
    GetIndicatorState.GetIndicatorStateBuilder javaBuilder = GetIndicatorState.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte[] pad4 = in.readPad(2);
    javaBuilder.deviceSpec(deviceSpec);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetIndicatorStateBuilder {
    public int getSize() {
      return 8;
    }
  }
}
