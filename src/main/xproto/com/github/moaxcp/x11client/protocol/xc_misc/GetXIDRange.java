package com.github.moaxcp.x11client.protocol.xc_misc;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetXIDRange implements TwoWayRequest<GetXIDRangeReply>, XcMiscObject {
  public static final byte OPCODE = 1;

  public XReplyFunction<GetXIDRangeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetXIDRangeReply.readGetXIDRangeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetXIDRange readGetXIDRange(X11Input in) throws IOException {
    GetXIDRange.GetXIDRangeBuilder javaBuilder = GetXIDRange.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public static class GetXIDRangeBuilder {
    public int getSize() {
      return 4;
    }
  }
}
