package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetDeviceControl implements TwoWayRequest<GetDeviceControlReply> {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 34;

  private short controlId;

  private byte deviceId;

  public XReplyFunction<GetDeviceControlReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetDeviceControlReply.readGetDeviceControlReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetDeviceControl readGetDeviceControl(X11Input in) throws IOException {
    GetDeviceControl.GetDeviceControlBuilder javaBuilder = GetDeviceControl.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short controlId = in.readCard16();
    byte deviceId = in.readCard8();
    byte[] pad5 = in.readPad(1);
    javaBuilder.controlId(controlId);
    javaBuilder.deviceId(deviceId);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(controlId);
    out.writeCard8(deviceId);
    out.writePad(1);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDeviceControlBuilder {
    public GetDeviceControl.GetDeviceControlBuilder controlId(DeviceControl controlId) {
      this.controlId = (short) controlId.getValue();
      return this;
    }

    public GetDeviceControl.GetDeviceControlBuilder controlId(short controlId) {
      this.controlId = controlId;
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
