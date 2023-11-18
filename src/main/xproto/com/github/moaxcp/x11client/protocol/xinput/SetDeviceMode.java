package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetDeviceMode implements TwoWayRequest<SetDeviceModeReply>, XinputObject {
  public static final byte OPCODE = 5;

  private byte deviceId;

  private byte mode;

  public XReplyFunction<SetDeviceModeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SetDeviceModeReply.readSetDeviceModeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetDeviceMode readSetDeviceMode(X11Input in) throws IOException {
    SetDeviceMode.SetDeviceModeBuilder javaBuilder = SetDeviceMode.builder();
    byte deviceId = in.readCard8();
    short length = in.readCard16();
    byte mode = in.readCard8();
    byte[] pad4 = in.readPad(2);
    javaBuilder.deviceId(deviceId);
    javaBuilder.mode(mode);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(deviceId);
    out.writeCard16((short) getLength());
    out.writeCard8(mode);
    out.writePad(2);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 7;
  }

  public static class SetDeviceModeBuilder {
    public SetDeviceMode.SetDeviceModeBuilder mode(ValuatorMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public SetDeviceMode.SetDeviceModeBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 7;
    }
  }
}
