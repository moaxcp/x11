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
public class GetDrawableInfo implements TwoWayRequest<GetDrawableInfoReply> {
  public static final String PLUGIN_NAME = "xf86dri";

  public static final byte OPCODE = 9;

  private int screen;

  private int drawable;

  public XReplyFunction<GetDrawableInfoReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetDrawableInfoReply.readGetDrawableInfoReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetDrawableInfo readGetDrawableInfo(X11Input in) throws IOException {
    GetDrawableInfo.GetDrawableInfoBuilder javaBuilder = GetDrawableInfo.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int screen = in.readCard32();
    int drawable = in.readCard32();
    javaBuilder.screen(screen);
    javaBuilder.drawable(drawable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(screen);
    out.writeCard32(drawable);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDrawableInfoBuilder {
    public int getSize() {
      return 12;
    }
  }
}
