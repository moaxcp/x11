package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class GetModifierMappingReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  @NonNull
  private ByteList keycodes;

  public static GetModifierMappingReply readGetModifierMappingReply(byte keycodesPerModifier,
      short sequenceNumber, X11Input in) throws IOException {
    GetModifierMappingReply.GetModifierMappingReplyBuilder javaBuilder = GetModifierMappingReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(24);
    ByteList keycodes = in.readCard8(Byte.toUnsignedInt(keycodesPerModifier) * 8);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.keycodes(keycodes.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    byte keycodesPerModifier = (byte) keycodes.size();
    out.writeCard8(keycodesPerModifier);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writePad(24);
    out.writeCard8(keycodes);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * keycodes.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetModifierMappingReplyBuilder {
    public int getSize() {
      return 32 + 1 * keycodes.size();
    }
  }
}
