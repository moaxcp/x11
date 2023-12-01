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
public class GetSeparableFilter implements TwoWayRequest<GetSeparableFilterReply>, GlxObject {
  public static final byte OPCODE = (byte) 153;

  private int contextTag;

  private int target;

  private int format;

  private int type;

  private boolean swapBytes;

  public XReplyFunction<GetSeparableFilterReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetSeparableFilterReply.readGetSeparableFilterReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetSeparableFilter readGetSeparableFilter(X11Input in) throws IOException {
    GetSeparableFilter.GetSeparableFilterBuilder javaBuilder = GetSeparableFilter.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int target = in.readCard32();
    int format = in.readCard32();
    int type = in.readCard32();
    boolean swapBytes = in.readBool();
    javaBuilder.contextTag(contextTag);
    javaBuilder.target(target);
    javaBuilder.format(format);
    javaBuilder.type(type);
    javaBuilder.swapBytes(swapBytes);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(target);
    out.writeCard32(format);
    out.writeCard32(type);
    out.writeBool(swapBytes);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 21;
  }

  public static class GetSeparableFilterBuilder {
    public int getSize() {
      return 21;
    }
  }
}
