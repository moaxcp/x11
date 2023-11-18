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
public class GetDrawableAttributes implements TwoWayRequest<GetDrawableAttributesReply>, GlxObject {
  public static final byte OPCODE = 29;

  private int drawable;

  public XReplyFunction<GetDrawableAttributesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetDrawableAttributesReply.readGetDrawableAttributesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetDrawableAttributes readGetDrawableAttributes(X11Input in) throws IOException {
    GetDrawableAttributes.GetDrawableAttributesBuilder javaBuilder = GetDrawableAttributes.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int drawable = in.readCard32();
    javaBuilder.drawable(drawable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class GetDrawableAttributesBuilder {
    public int getSize() {
      return 8;
    }
  }
}
