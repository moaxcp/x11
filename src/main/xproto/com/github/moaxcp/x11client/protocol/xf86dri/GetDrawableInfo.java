package com.github.moaxcp.x11client.protocol.xf86dri;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetDrawableInfo implements TwoWayRequest<GetDrawableInfoReply>, Xf86driObject {
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
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int screen = in.readCard32();
    int drawable = in.readCard32();
    javaBuilder.screen(screen);
    javaBuilder.drawable(drawable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(screen);
    out.writeCard32(drawable);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GetDrawableInfoBuilder {
    public int getSize() {
      return 12;
    }
  }
}
