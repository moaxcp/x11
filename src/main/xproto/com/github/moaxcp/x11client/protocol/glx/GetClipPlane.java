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
public class GetClipPlane implements TwoWayRequest<GetClipPlaneReply>, GlxObject {
  public static final byte OPCODE = 113;

  private int contextTag;

  private int plane;

  public XReplyFunction<GetClipPlaneReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetClipPlaneReply.readGetClipPlaneReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetClipPlane readGetClipPlane(X11Input in) throws IOException {
    GetClipPlane.GetClipPlaneBuilder javaBuilder = GetClipPlane.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int plane = in.readInt32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.plane(plane);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeInt32(plane);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GetClipPlaneBuilder {
    public int getSize() {
      return 12;
    }
  }
}
