package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetCursorName implements TwoWayRequest<GetCursorNameReply>, XfixesObject {
  public static final byte OPCODE = 24;

  private int cursor;

  public XReplyFunction<GetCursorNameReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetCursorNameReply.readGetCursorNameReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetCursorName readGetCursorName(X11Input in) throws IOException {
    GetCursorName.GetCursorNameBuilder javaBuilder = GetCursorName.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cursor = in.readCard32();
    javaBuilder.cursor(cursor);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cursor);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class GetCursorNameBuilder {
    public int getSize() {
      return 8;
    }
  }
}
