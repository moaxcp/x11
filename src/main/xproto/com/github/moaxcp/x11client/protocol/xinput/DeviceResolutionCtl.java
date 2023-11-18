package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DeviceResolutionCtl implements XStruct, XinputObject {
  private short controlId;

  private short len;

  private byte firstValuator;

  @NonNull
  private List<Integer> resolutionValues;

  public static DeviceResolutionCtl readDeviceResolutionCtl(X11Input in) throws IOException {
    DeviceResolutionCtl.DeviceResolutionCtlBuilder javaBuilder = DeviceResolutionCtl.builder();
    short controlId = in.readCard16();
    short len = in.readCard16();
    byte firstValuator = in.readCard8();
    byte numValuators = in.readCard8();
    byte[] pad4 = in.readPad(2);
    List<Integer> resolutionValues = in.readCard32(Byte.toUnsignedInt(numValuators));
    javaBuilder.controlId(controlId);
    javaBuilder.len(len);
    javaBuilder.firstValuator(firstValuator);
    javaBuilder.resolutionValues(resolutionValues);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(controlId);
    out.writeCard16(len);
    out.writeCard8(firstValuator);
    byte numValuators = (byte) resolutionValues.size();
    out.writeCard8(numValuators);
    out.writePad(2);
    out.writeCard32(resolutionValues);
  }

  @Override
  public int getSize() {
    return 8 + 4 * resolutionValues.size();
  }

  public static class DeviceResolutionCtlBuilder {
    public DeviceResolutionCtl.DeviceResolutionCtlBuilder controlId(DeviceControl controlId) {
      this.controlId = (short) controlId.getValue();
      return this;
    }

    public DeviceResolutionCtl.DeviceResolutionCtlBuilder controlId(short controlId) {
      this.controlId = controlId;
      return this;
    }

    public int getSize() {
      return 8 + 4 * resolutionValues.size();
    }
  }
}
