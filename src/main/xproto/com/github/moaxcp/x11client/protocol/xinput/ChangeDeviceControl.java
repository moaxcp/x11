package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ChangeDeviceControl implements TwoWayRequest<ChangeDeviceControlReply>, XinputObject {
  public static final byte OPCODE = 35;

  private short controlId;

  private byte deviceId;

  @NonNull
  private DeviceCtl control;

  public XReplyFunction<ChangeDeviceControlReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ChangeDeviceControlReply.readChangeDeviceControlReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeDeviceControl readChangeDeviceControl(X11Input in) throws IOException {
    ChangeDeviceControl.ChangeDeviceControlBuilder javaBuilder = ChangeDeviceControl.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short controlId = in.readCard16();
    byte deviceId = in.readCard8();
    byte[] pad5 = in.readPad(1);
    DeviceCtl control = DeviceCtl.readDeviceCtl(in);
    javaBuilder.controlId(controlId);
    javaBuilder.deviceId(deviceId);
    javaBuilder.control(control);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(controlId);
    out.writeCard8(deviceId);
    out.writePad(1);
    control.write(out);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class ChangeDeviceControlBuilder {
    public ChangeDeviceControl.ChangeDeviceControlBuilder controlId(DeviceControl controlId) {
      this.controlId = (short) controlId.getValue();
      return this;
    }

    public ChangeDeviceControl.ChangeDeviceControlBuilder controlId(short controlId) {
      this.controlId = controlId;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
