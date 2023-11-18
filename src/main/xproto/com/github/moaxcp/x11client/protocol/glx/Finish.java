package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Finish implements TwoWayRequest<FinishReply>, GlxObject {
  public static final byte OPCODE = 108;

  private int contextTag;

  public XReplyFunction<FinishReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> FinishReply.readFinishReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static Finish readFinish(X11Input in) throws IOException {
    Finish.FinishBuilder javaBuilder = Finish.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    javaBuilder.contextTag(contextTag);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class FinishBuilder {
    public int getSize() {
      return 8;
    }
  }
}
