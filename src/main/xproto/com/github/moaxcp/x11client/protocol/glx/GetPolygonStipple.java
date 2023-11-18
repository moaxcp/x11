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
public class GetPolygonStipple implements TwoWayRequest<GetPolygonStippleReply>, GlxObject {
  public static final byte OPCODE = (byte) 128;

  private int contextTag;

  private boolean lsbFirst;

  public XReplyFunction<GetPolygonStippleReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetPolygonStippleReply.readGetPolygonStippleReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetPolygonStipple readGetPolygonStipple(X11Input in) throws IOException {
    GetPolygonStipple.GetPolygonStippleBuilder javaBuilder = GetPolygonStipple.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    boolean lsbFirst = in.readBool();
    javaBuilder.contextTag(contextTag);
    javaBuilder.lsbFirst(lsbFirst);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeBool(lsbFirst);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 9;
  }

  public static class GetPolygonStippleBuilder {
    public int getSize() {
      return 9;
    }
  }
}
