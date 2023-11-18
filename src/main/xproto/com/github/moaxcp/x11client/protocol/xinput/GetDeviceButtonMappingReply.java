package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetDeviceButtonMappingReply implements XReply, XinputObject {
  private byte xiReplyType;

  private short sequenceNumber;

  @NonNull
  private List<Byte> map;

  public static GetDeviceButtonMappingReply readGetDeviceButtonMappingReply(byte xiReplyType,
      short sequenceNumber, X11Input in) throws IOException {
    GetDeviceButtonMappingReply.GetDeviceButtonMappingReplyBuilder javaBuilder = GetDeviceButtonMappingReply.builder();
    int length = in.readCard32();
    byte mapSize = in.readCard8();
    byte[] pad5 = in.readPad(23);
    List<Byte> map = in.readCard8(Byte.toUnsignedInt(mapSize));
    in.readPadAlign(Byte.toUnsignedInt(mapSize));
    javaBuilder.xiReplyType(xiReplyType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.map(map);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
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

  public static class GetDeviceButtonMappingReplyBuilder {
    public int getSize() {
      return 32 + 1 * map.size() + XObject.getSizeForPadAlign(4, 1 * map.size());
    }
  }
}
