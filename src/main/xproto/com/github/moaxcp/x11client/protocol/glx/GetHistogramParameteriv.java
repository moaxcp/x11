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
public class GetHistogramParameteriv implements TwoWayRequest<GetHistogramParameterivReply>, GlxObject {
  public static final byte OPCODE = (byte) 156;

  private int contextTag;

  private int target;

  private int pname;

  public XReplyFunction<GetHistogramParameterivReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetHistogramParameterivReply.readGetHistogramParameterivReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetHistogramParameteriv readGetHistogramParameteriv(X11Input in) throws
      IOException {
    GetHistogramParameteriv.GetHistogramParameterivBuilder javaBuilder = GetHistogramParameteriv.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int target = in.readCard32();
    int pname = in.readCard32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.target(target);
    javaBuilder.pname(pname);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(target);
    out.writeCard32(pname);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class GetHistogramParameterivBuilder {
    public int getSize() {
      return 16;
    }
  }
}
