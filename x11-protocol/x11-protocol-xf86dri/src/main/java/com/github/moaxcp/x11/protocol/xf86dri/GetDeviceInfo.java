package com.github.moaxcp.x11.protocol.xf86dri;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetDeviceInfo implements TwoWayRequest<GetDeviceInfoReply> {
  public static final String PLUGIN_NAME = "xf86dri";

  public static final byte OPCODE = 10;

  private int screen;

  public XReplyFunction<GetDeviceInfoReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetDeviceInfoReply.readGetDeviceInfoReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetDeviceInfo readGetDeviceInfo(X11Input in) throws IOException {
    GetDeviceInfo.GetDeviceInfoBuilder javaBuilder = GetDeviceInfo.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int screen = in.readCard32();
    javaBuilder.screen(screen);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(screen);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDeviceInfoBuilder {
    public int getSize() {
      return 8;
    }
  }
}
