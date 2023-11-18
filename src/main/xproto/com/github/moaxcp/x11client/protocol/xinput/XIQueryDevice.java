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
public class XIQueryDevice implements TwoWayRequest<XIQueryDeviceReply>, XinputObject {
  public static final byte OPCODE = 48;

  private short deviceid;

  public XReplyFunction<XIQueryDeviceReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> XIQueryDeviceReply.readXIQueryDeviceReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIQueryDevice readXIQueryDevice(X11Input in) throws IOException {
    XIQueryDevice.XIQueryDeviceBuilder javaBuilder = XIQueryDevice.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short deviceid = in.readCard16();
    byte[] pad4 = in.readPad(2);
    javaBuilder.deviceid(deviceid);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(deviceid);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class XIQueryDeviceBuilder {
    public int getSize() {
      return 8;
    }
  }
}
