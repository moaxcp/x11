package com.github.moaxcp.x11client.protocol.dri3;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FDFromFence implements TwoWayRequest<FDFromFenceReply>, Dri3Object {
  public static final byte OPCODE = 5;

  private int drawable;

  private int fence;

  public XReplyFunction<FDFromFenceReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> FDFromFenceReply.readFDFromFenceReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static FDFromFence readFDFromFence(X11Input in) throws IOException {
    FDFromFence.FDFromFenceBuilder javaBuilder = FDFromFence.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int drawable = in.readCard32();
    int fence = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.fence(fence);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(fence);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class FDFromFenceBuilder {
    public int getSize() {
      return 12;
    }
  }
}
