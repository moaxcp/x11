package com.github.moaxcp.x11client.protocol.xselinux;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetDeviceContext implements TwoWayRequest<GetDeviceContextReply>, XselinuxObject {
  public static final byte OPCODE = 4;

  private int device;

  public XReplyFunction<GetDeviceContextReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetDeviceContextReply.readGetDeviceContextReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetDeviceContext readGetDeviceContext(X11Input in) throws IOException {
    GetDeviceContext.GetDeviceContextBuilder javaBuilder = GetDeviceContext.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int device = in.readCard32();
    javaBuilder.device(device);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(device);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class GetDeviceContextBuilder {
    public int getSize() {
      return 8;
    }
  }
}
