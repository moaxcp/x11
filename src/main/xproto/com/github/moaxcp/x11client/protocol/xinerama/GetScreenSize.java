package com.github.moaxcp.x11client.protocol.xinerama;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetScreenSize implements TwoWayRequest<GetScreenSizeReply>, XineramaObject {
  public static final byte OPCODE = 3;

  private int window;

  private int screen;

  public XReplyFunction<GetScreenSizeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetScreenSizeReply.readGetScreenSizeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetScreenSize readGetScreenSize(X11Input in) throws IOException {
    GetScreenSize.GetScreenSizeBuilder javaBuilder = GetScreenSize.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    int screen = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.screen(screen);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(screen);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GetScreenSizeBuilder {
    public int getSize() {
      return 12;
    }
  }
}
