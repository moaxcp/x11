package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeletePointerBarrier implements OneWayRequest, XfixesObject {
  public static final byte OPCODE = 32;

  private int barrier;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeletePointerBarrier readDeletePointerBarrier(X11Input in) throws IOException {
    DeletePointerBarrier.DeletePointerBarrierBuilder javaBuilder = DeletePointerBarrier.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int barrier = in.readCard32();
    javaBuilder.barrier(barrier);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(barrier);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class DeletePointerBarrierBuilder {
    public int getSize() {
      return 8;
    }
  }
}
