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
public class RenderMode implements TwoWayRequest<RenderModeReply>, GlxObject {
  public static final byte OPCODE = 107;

  private int contextTag;

  private int mode;

  public XReplyFunction<RenderModeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> RenderModeReply.readRenderModeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static RenderMode readRenderMode(X11Input in) throws IOException {
    RenderMode.RenderModeBuilder javaBuilder = RenderMode.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int mode = in.readCard32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.mode(mode);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(mode);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class RenderModeBuilder {
    public int getSize() {
      return 12;
    }
  }
}
