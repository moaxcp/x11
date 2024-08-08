package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class SetModifierMapping implements TwoWayRequest<SetModifierMappingReply> {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 118;

  @NonNull
  private ByteList keycodes;

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
    ByteList keycodes = in.readCard8(Byte.toUnsignedInt(keycodesPerModifier) * 8);
    javaBuilder.keycodes(keycodes.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetModifierMappingBuilder {
    public int getSize() {
      return 4 + 1 * keycodes.size();
    }
  }
}
