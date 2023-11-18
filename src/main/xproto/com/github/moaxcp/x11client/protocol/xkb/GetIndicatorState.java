package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetIndicatorState implements TwoWayRequest<GetIndicatorStateReply>, XkbObject {
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
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte[] pad4 = in.readPad(2);
    javaBuilder.deviceSpec(deviceSpec);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class GetIndicatorStateBuilder {
    public int getSize() {
      return 8;
    }
  }
}
