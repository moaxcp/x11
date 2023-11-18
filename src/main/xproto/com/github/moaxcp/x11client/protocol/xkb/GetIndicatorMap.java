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
public class GetIndicatorMap implements TwoWayRequest<GetIndicatorMapReply>, XkbObject {
  public static final byte OPCODE = 13;

  private short deviceSpec;

  private int which;

  public XReplyFunction<GetIndicatorMapReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetIndicatorMapReply.readGetIndicatorMapReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetIndicatorMap readGetIndicatorMap(X11Input in) throws IOException {
    GetIndicatorMap.GetIndicatorMapBuilder javaBuilder = GetIndicatorMap.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte[] pad4 = in.readPad(2);
    int which = in.readCard32();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.which(which);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writePad(2);
    out.writeCard32(which);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GetIndicatorMapBuilder {
    public int getSize() {
      return 12;
    }
  }
}
