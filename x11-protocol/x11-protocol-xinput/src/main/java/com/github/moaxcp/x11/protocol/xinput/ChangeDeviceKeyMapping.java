package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class ChangeDeviceKeyMapping implements OneWayRequest {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 25;

  private byte deviceId;

  private byte firstKeycode;

  private byte keysymsPerKeycode;

  private byte keycodeCount;

  @NonNull
  private IntList keysyms;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeDeviceKeyMapping readChangeDeviceKeyMapping(X11Input in) throws IOException {
    ChangeDeviceKeyMapping.ChangeDeviceKeyMappingBuilder javaBuilder = ChangeDeviceKeyMapping.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte deviceId = in.readCard8();
    byte firstKeycode = in.readCard8();
    byte keysymsPerKeycode = in.readCard8();
    byte keycodeCount = in.readCard8();
    IntList keysyms = in.readCard32(Byte.toUnsignedInt(keycodeCount) * Byte.toUnsignedInt(keysymsPerKeycode));
    javaBuilder.deviceId(deviceId);
    javaBuilder.firstKeycode(firstKeycode);
    javaBuilder.keysymsPerKeycode(keysymsPerKeycode);
    javaBuilder.keycodeCount(keycodeCount);
    javaBuilder.keysyms(keysyms.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(deviceId);
    out.writeCard8(firstKeycode);
    out.writeCard8(keysymsPerKeycode);
    out.writeCard8(keycodeCount);
    out.writeCard32(keysyms);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 4 * keysyms.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ChangeDeviceKeyMappingBuilder {
    public int getSize() {
      return 8 + 4 * keysyms.size();
    }
  }
}
