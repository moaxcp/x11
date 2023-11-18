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
public class BuffersFromPixmap implements TwoWayRequest<BuffersFromPixmapReply>, Dri3Object {
  public static final byte OPCODE = 8;

  private int pixmap;

  public XReplyFunction<BuffersFromPixmapReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> BuffersFromPixmapReply.readBuffersFromPixmapReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static BuffersFromPixmap readBuffersFromPixmap(X11Input in) throws IOException {
    BuffersFromPixmap.BuffersFromPixmapBuilder javaBuilder = BuffersFromPixmap.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int pixmap = in.readCard32();
    javaBuilder.pixmap(pixmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(pixmap);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class BuffersFromPixmapBuilder {
    public int getSize() {
      return 8;
    }
  }
}
