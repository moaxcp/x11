package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class GetDeviceButtonMappingReply implements XReply {
  public static final String PLUGIN_NAME = "xinput";

  private byte xiReplyType;

  private short sequenceNumber;

  @NonNull
  private ByteList map;

  public static GetDeviceButtonMappingReply readGetDeviceButtonMappingReply(byte xiReplyType,
      short sequenceNumber, X11Input in) throws IOException {
    GetDeviceButtonMappingReply.GetDeviceButtonMappingReplyBuilder javaBuilder = GetDeviceButtonMappingReply.builder();
    int length = in.readCard32();
    byte mapSize = in.readCard8();
    byte[] pad5 = in.readPad(23);
    ByteList map = in.readCard8(Byte.toUnsignedInt(mapSize));
    in.readPadAlign(Byte.toUnsignedInt(mapSize));
    javaBuilder.xiReplyType(xiReplyType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.map(map.toImmutable());
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
    byte mapSize = (byte) map.size();
    out.writeCard8(mapSize);
    out.writePad(23);
    out.writeCard8(map);
    out.writePadAlign(Byte.toUnsignedInt(mapSize));
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * map.size() + XObject.getSizeForPadAlign(4, 1 * map.size());
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDeviceButtonMappingReplyBuilder {
    public int getSize() {
      return 32 + 1 * map.size() + XObject.getSizeForPadAlign(4, 1 * map.size());
    }
  }
}
