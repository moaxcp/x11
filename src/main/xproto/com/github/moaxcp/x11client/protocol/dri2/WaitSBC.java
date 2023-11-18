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
public class WaitSBC implements TwoWayRequest<WaitSBCReply>, Dri2Object {
  public static final byte OPCODE = 11;

  private int drawable;

  private int targetSbcHi;

  private int targetSbcLo;

  public XReplyFunction<WaitSBCReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> WaitSBCReply.readWaitSBCReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static WaitSBC readWaitSBC(X11Input in) throws IOException {
    WaitSBC.WaitSBCBuilder javaBuilder = WaitSBC.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int drawable = in.readCard32();
    int targetSbcHi = in.readCard32();
    int targetSbcLo = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.targetSbcHi(targetSbcHi);
    javaBuilder.targetSbcLo(targetSbcLo);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(targetSbcHi);
    out.writeCard32(targetSbcLo);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class WaitSBCBuilder {
    public int getSize() {
      return 16;
    }
  }
}
