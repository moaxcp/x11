package com.github.moaxcp.x11client.protocol.xinput;

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
public class SetDeviceButtonMapping implements TwoWayRequest<SetDeviceButtonMappingReply>, XinputObject {
  public static final byte OPCODE = 29;

  private byte deviceId;

  @NonNull
  private List<Byte> map;

  public XReplyFunction<SetDeviceButtonMappingReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SetDeviceButtonMappingReply.readSetDeviceButtonMappingReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetDeviceButtonMapping readSetDeviceButtonMapping(X11Input in) throws IOException {
    SetDeviceButtonMapping.SetDeviceButtonMappingBuilder javaBuilder = SetDeviceButtonMapping.builder();
    byte deviceId = in.readCard8();
    short length = in.readCard16();
    byte mapSize = in.readCard8();
    byte[] pad4 = in.readPad(2);
    List<Byte> map = in.readCard8(Byte.toUnsignedInt(mapSize));
    javaBuilder.deviceId(deviceId);
    javaBuilder.map(map);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(deviceId);
    out.writeCard16((short) getLength());
    byte mapSize = (byte) map.size();
    out.writeCard8(mapSize);
    out.writePad(2);
    out.writeCard8(map);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 7 + 1 * map.size();
  }

  public static class SetDeviceButtonMappingBuilder {
    public int getSize() {
      return 7 + 1 * map.size();
    }
  }
}
