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
public class SetModifierMapping implements TwoWayRequest<SetModifierMappingReply>, XprotoObject {
  public static final byte OPCODE = 118;

  @NonNull
  private List<Byte> keycodes;

  public XReplyFunction<SetModifierMappingReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SetModifierMappingReply.readSetModifierMappingReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetModifierMapping readSetModifierMapping(X11Input in) throws IOException {
    SetModifierMapping.SetModifierMappingBuilder javaBuilder = SetModifierMapping.builder();
    byte keycodesPerModifier = in.readCard8();
    short length = in.readCard16();
    List<Byte> keycodes = in.readCard8(Byte.toUnsignedInt(keycodesPerModifier) * 8);
    javaBuilder.keycodes(keycodes);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    byte keycodesPerModifier = (byte) keycodes.size();
    out.writeCard8(keycodesPerModifier);
    out.writeCard16((short) getLength());
    out.writeCard8(keycodes);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 4 + 1 * keycodes.size();
  }

  public static class SetModifierMappingBuilder {
    public int getSize() {
      return 4 + 1 * keycodes.size();
    }
  }
}
