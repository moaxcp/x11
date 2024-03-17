package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetFBConfigs implements TwoWayRequest<GetFBConfigsReply> {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 21;

  private int screen;

  public XReplyFunction<GetFBConfigsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetFBConfigsReply.readGetFBConfigsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetFBConfigs readGetFBConfigs(X11Input in) throws IOException {
    GetFBConfigs.GetFBConfigsBuilder javaBuilder = GetFBConfigs.builder();
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

  public static class GetFBConfigsBuilder {
    public int getSize() {
      return 8;
    }
  }
}
