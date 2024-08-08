package com.github.moaxcp.x11.protocol.xinput;

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
public class GetDeviceModifierMappingReply implements XReply {
  public static final String PLUGIN_NAME = "xinput";

  private byte xiReplyType;

  private short sequenceNumber;

  @NonNull
  private ByteList keymaps;

  public static GetDeviceModifierMappingReply readGetDeviceModifierMappingReply(byte xiReplyType,
      short sequenceNumber, X11Input in) throws IOException {
    GetDeviceModifierMappingReply.GetDeviceModifierMappingReplyBuilder javaBuilder = GetDeviceModifierMappingReply.builder();
    int length = in.readCard32();
    byte keycodesPerModifier = in.readCard8();
    byte[] pad5 = in.readPad(23);
    ByteList keymaps = in.readCard8(Byte.toUnsignedInt(keycodesPerModifier) * 8);
    javaBuilder.xiReplyType(xiReplyType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.keymaps(keymaps.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(xiReplyType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    byte keycodesPerModifier = (byte) keymaps.size();
    out.writeCard8(keycodesPerModifier);
    out.writePad(23);
    out.writeCard8(keymaps);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * keymaps.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDeviceModifierMappingReplyBuilder {
    public int getSize() {
      return 32 + 1 * keymaps.size();
    }
  }
}
