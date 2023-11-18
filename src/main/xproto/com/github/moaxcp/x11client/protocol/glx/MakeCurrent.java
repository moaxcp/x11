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
public class MakeCurrent implements TwoWayRequest<MakeCurrentReply>, GlxObject {
  public static final byte OPCODE = 5;

  private int drawable;

  private int context;

  private int oldContextTag;

  public XReplyFunction<MakeCurrentReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> MakeCurrentReply.readMakeCurrentReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static MakeCurrent readMakeCurrent(X11Input in) throws IOException {
    MakeCurrent.MakeCurrentBuilder javaBuilder = MakeCurrent.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int drawable = in.readCard32();
    int context = in.readCard32();
    int oldContextTag = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.context(context);
    javaBuilder.oldContextTag(oldContextTag);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(context);
    out.writeCard32(oldContextTag);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class MakeCurrentBuilder {
    public int getSize() {
      return 16;
    }
  }
}
