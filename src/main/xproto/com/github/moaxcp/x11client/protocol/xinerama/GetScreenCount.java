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
public class GetScreenCount implements TwoWayRequest<GetScreenCountReply>, XineramaObject {
  public static final byte OPCODE = 2;

  private int window;

  public XReplyFunction<GetScreenCountReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetScreenCountReply.readGetScreenCountReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetScreenCount readGetScreenCount(X11Input in) throws IOException {
    GetScreenCount.GetScreenCountBuilder javaBuilder = GetScreenCount.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class GetScreenCountBuilder {
    public int getSize() {
      return 8;
    }
  }
}
