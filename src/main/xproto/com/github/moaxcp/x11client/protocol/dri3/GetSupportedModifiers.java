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
public class GetSupportedModifiers implements TwoWayRequest<GetSupportedModifiersReply>, Dri3Object {
  public static final byte OPCODE = 6;

  private int window;

  private byte depth;

  private byte bpp;

  public XReplyFunction<GetSupportedModifiersReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetSupportedModifiersReply.readGetSupportedModifiersReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetSupportedModifiers readGetSupportedModifiers(X11Input in) throws IOException {
    GetSupportedModifiers.GetSupportedModifiersBuilder javaBuilder = GetSupportedModifiers.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    byte depth = in.readCard8();
    byte bpp = in.readCard8();
    byte[] pad6 = in.readPad(2);
    javaBuilder.window(window);
    javaBuilder.depth(depth);
    javaBuilder.bpp(bpp);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard8(depth);
    out.writeCard8(bpp);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GetSupportedModifiersBuilder {
    public int getSize() {
      return 12;
    }
  }
}
