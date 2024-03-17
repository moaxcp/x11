package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeleteDeviceProperty implements OneWayRequest {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 38;

  private int property;

  private byte deviceId;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeleteDeviceProperty readDeleteDeviceProperty(X11Input in) throws IOException {
    DeleteDeviceProperty.DeleteDevicePropertyBuilder javaBuilder = DeleteDeviceProperty.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int property = in.readCard32();
    byte deviceId = in.readCard8();
    byte[] pad5 = in.readPad(3);
    javaBuilder.property(property);
    javaBuilder.deviceId(deviceId);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(property);
    out.writeCard8(deviceId);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DeleteDevicePropertyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
