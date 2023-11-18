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
public class IsList implements TwoWayRequest<IsListReply>, GlxObject {
  public static final byte OPCODE = (byte) 141;

  private int contextTag;

  private int list;

  public XReplyFunction<IsListReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> IsListReply.readIsListReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static IsList readIsList(X11Input in) throws IOException {
    IsList.IsListBuilder javaBuilder = IsList.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int list = in.readCard32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.list(list);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(list);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class IsListBuilder {
    public int getSize() {
      return 12;
    }
  }
}
