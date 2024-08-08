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
public class SetPointerMapping implements TwoWayRequest<SetPointerMappingReply> {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 116;

  @NonNull
  private ByteList map;

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
    ByteList map = in.readCard8(Byte.toUnsignedInt(mapLen));
    javaBuilder.map(map.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetPointerMappingBuilder {
    public int getSize() {
      return 4 + 1 * map.size();
    }
  }
}
