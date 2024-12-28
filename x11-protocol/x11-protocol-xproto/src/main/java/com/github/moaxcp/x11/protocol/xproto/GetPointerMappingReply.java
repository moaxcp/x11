package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetPointerMappingReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  @NonNull
  private List<Byte> map;

  public static GetPointerMappingReply readGetPointerMappingReply(byte mapLen, short sequenceNumber,
      X11Input in) throws IOException {
    GetPointerMappingReply.GetPointerMappingReplyBuilder javaBuilder = GetPointerMappingReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(24);
    List<Byte> map = in.readCard8(Byte.toUnsignedInt(mapLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.map(map);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    byte mapLen = (byte) map.size();
    out.writeCard8(mapLen);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writePad(24);
    out.writeCard8(map);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * map.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetPointerMappingReplyBuilder {
    public int getSize() {
      return 32 + 1 * map.size();
    }
  }
}
