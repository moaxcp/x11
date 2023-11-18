package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ChangeDeviceKeyMapping implements OneWayRequest, XinputObject {
  public static final byte OPCODE = 25;

  private byte deviceId;

  private byte firstKeycode;

  private byte keysymsPerKeycode;

  private byte keycodeCount;

  @NonNull
  private List<Integer> keysyms;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeDeviceKeyMapping readChangeDeviceKeyMapping(X11Input in) throws IOException {
    ChangeDeviceKeyMapping.ChangeDeviceKeyMappingBuilder javaBuilder = ChangeDeviceKeyMapping.builder();
    byte deviceId = in.readCard8();
    short length = in.readCard16();
    byte firstKeycode = in.readCard8();
    byte keysymsPerKeycode = in.readCard8();
    byte keycodeCount = in.readCard8();
    List<Integer> keysyms = in.readCard32(Byte.toUnsignedInt(keycodeCount) * Byte.toUnsignedInt(keysymsPerKeycode));
    javaBuilder.deviceId(deviceId);
    javaBuilder.firstKeycode(firstKeycode);
    javaBuilder.keysymsPerKeycode(keysymsPerKeycode);
    javaBuilder.keycodeCount(keycodeCount);
    javaBuilder.keysyms(keysyms);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(deviceId);
    out.writeCard16((short) getLength());
    out.writeCard8(firstKeycode);
    out.writeCard8(keysymsPerKeycode);
    out.writeCard8(keycodeCount);
    out.writeCard32(keysyms);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 7 + 4 * keysyms.size();
  }

  public static class ChangeDeviceKeyMappingBuilder {
    public int getSize() {
      return 7 + 4 * keysyms.size();
    }
  }
}
