package com.github.moaxcp.x11client.protocol.dri2;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SwapBuffers implements TwoWayRequest<SwapBuffersReply>, Dri2Object {
  public static final byte OPCODE = 8;

  private int drawable;

  private int targetMscHi;

  private int targetMscLo;

  private int divisorHi;

  private int divisorLo;

  private int remainderHi;

  private int remainderLo;

  public XReplyFunction<SwapBuffersReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SwapBuffersReply.readSwapBuffersReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SwapBuffers readSwapBuffers(X11Input in) throws IOException {
    SwapBuffers.SwapBuffersBuilder javaBuilder = SwapBuffers.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int drawable = in.readCard32();
    int targetMscHi = in.readCard32();
    int targetMscLo = in.readCard32();
    int divisorHi = in.readCard32();
    int divisorLo = in.readCard32();
    int remainderHi = in.readCard32();
    int remainderLo = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.targetMscHi(targetMscHi);
    javaBuilder.targetMscLo(targetMscLo);
    javaBuilder.divisorHi(divisorHi);
    javaBuilder.divisorLo(divisorLo);
    javaBuilder.remainderHi(remainderHi);
    javaBuilder.remainderLo(remainderLo);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(targetMscHi);
    out.writeCard32(targetMscLo);
    out.writeCard32(divisorHi);
    out.writeCard32(divisorLo);
    out.writeCard32(remainderHi);
    out.writeCard32(remainderLo);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class SwapBuffersBuilder {
    public int getSize() {
      return 32;
    }
  }
}
