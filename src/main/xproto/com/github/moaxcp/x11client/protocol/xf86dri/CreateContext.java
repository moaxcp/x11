package com.github.moaxcp.x11client.protocol.xf86dri;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateContext implements TwoWayRequest<CreateContextReply>, Xf86driObject {
  public static final byte OPCODE = 5;

  private int screen;

  private int visual;

  private int context;

  public XReplyFunction<CreateContextReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> CreateContextReply.readCreateContextReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateContext readCreateContext(X11Input in) throws IOException {
    CreateContext.CreateContextBuilder javaBuilder = CreateContext.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int screen = in.readCard32();
    int visual = in.readCard32();
    int context = in.readCard32();
    javaBuilder.screen(screen);
    javaBuilder.visual(visual);
    javaBuilder.context(context);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(screen);
    out.writeCard32(visual);
    out.writeCard32(context);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class CreateContextBuilder {
    public int getSize() {
      return 16;
    }
  }
}
