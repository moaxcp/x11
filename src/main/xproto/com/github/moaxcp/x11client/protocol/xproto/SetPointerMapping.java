package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetPointerMapping implements TwoWayRequest<SetPointerMappingReply>, XprotoObject {
  public static final byte OPCODE = 116;

  @NonNull
  private List<Byte> map;

  public XReplyFunction<SetPointerMappingReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SetPointerMappingReply.readSetPointerMappingReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetPointerMapping readSetPointerMapping(X11Input in) throws IOException {
    SetPointerMapping.SetPointerMappingBuilder javaBuilder = SetPointerMapping.builder();
    byte mapLen = in.readCard8();
    short length = in.readCard16();
    List<Byte> map = in.readCard8(Byte.toUnsignedInt(mapLen));
    javaBuilder.map(map);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    byte mapLen = (byte) map.size();
    out.writeCard8(mapLen);
    out.writeCard16((short) getLength());
    out.writeCard8(map);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 4 + 1 * map.size();
  }

  public static class SetPointerMappingBuilder {
    public int getSize() {
      return 4 + 1 * map.size();
    }
  }
}
