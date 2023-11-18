package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetModifierMappingReply implements XReply, XprotoObject {
  private short sequenceNumber;

  @NonNull
  private List<Byte> keycodes;

  public static GetModifierMappingReply readGetModifierMappingReply(byte keycodesPerModifier,
      short sequenceNumber, X11Input in) throws IOException {
    GetModifierMappingReply.GetModifierMappingReplyBuilder javaBuilder = GetModifierMappingReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(24);
    List<Byte> keycodes = in.readCard8(Byte.toUnsignedInt(keycodesPerModifier) * 8);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.keycodes(keycodes);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
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

  public static class GetModifierMappingReplyBuilder {
    public int getSize() {
      return 32 + 1 * keycodes.size();
    }
  }
}
