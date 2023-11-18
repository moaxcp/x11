package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetKeyboardMapping implements TwoWayRequest<GetKeyboardMappingReply>, XprotoObject {
  public static final byte OPCODE = 101;

  private byte firstKeycode;

  private byte count;

  public XReplyFunction<GetKeyboardMappingReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetKeyboardMappingReply.readGetKeyboardMappingReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetKeyboardMapping readGetKeyboardMapping(X11Input in) throws IOException {
    GetKeyboardMapping.GetKeyboardMappingBuilder javaBuilder = GetKeyboardMapping.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    byte firstKeycode = in.readCard8();
    byte count = in.readCard8();
    javaBuilder.firstKeycode(firstKeycode);
    javaBuilder.count(count);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard8(firstKeycode);
    out.writeCard8(count);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 6;
  }

  public static class GetKeyboardMappingBuilder {
    public int getSize() {
      return 6;
    }
  }
}
