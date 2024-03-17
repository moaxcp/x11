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
public class ChangePointerDevice implements TwoWayRequest<ChangePointerDeviceReply> {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 12;

  private byte xAxis;

  private byte yAxis;

  private byte deviceId;

  public XReplyFunction<ChangePointerDeviceReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ChangePointerDeviceReply.readChangePointerDeviceReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangePointerDevice readChangePointerDevice(X11Input in) throws IOException {
    ChangePointerDevice.ChangePointerDeviceBuilder javaBuilder = ChangePointerDevice.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte xAxis = in.readCard8();
    byte yAxis = in.readCard8();
    byte deviceId = in.readCard8();
    byte[] pad6 = in.readPad(1);
    javaBuilder.xAxis(xAxis);
    javaBuilder.yAxis(yAxis);
    javaBuilder.deviceId(deviceId);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(xAxis);
    out.writeCard8(yAxis);
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

  public static class ChangePointerDeviceBuilder {
    public int getSize() {
      return 8;
    }
  }
}
