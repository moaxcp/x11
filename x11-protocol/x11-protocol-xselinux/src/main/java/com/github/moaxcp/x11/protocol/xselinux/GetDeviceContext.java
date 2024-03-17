package com.github.moaxcp.x11.protocol.xselinux;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetDeviceContext implements TwoWayRequest<GetDeviceContextReply> {
  public static final String PLUGIN_NAME = "xselinux";

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
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int device = in.readCard32();
    javaBuilder.device(device);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(device);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDeviceContextBuilder {
    public int getSize() {
      return 8;
    }
  }
}
