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
public class GetPixelMapuiv implements TwoWayRequest<GetPixelMapuivReply>, GlxObject {
  public static final byte OPCODE = 126;

  private int contextTag;

  private int map;

  public XReplyFunction<GetPixelMapuivReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetPixelMapuivReply.readGetPixelMapuivReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetPixelMapuiv readGetPixelMapuiv(X11Input in) throws IOException {
    GetPixelMapuiv.GetPixelMapuivBuilder javaBuilder = GetPixelMapuiv.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int map = in.readCard32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.map(map);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(map);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GetPixelMapuivBuilder {
    public int getSize() {
      return 12;
    }
  }
}
