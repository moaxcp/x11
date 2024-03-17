package com.github.moaxcp.x11.protocol.dri3;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BufferFromPixmap implements TwoWayRequest<BufferFromPixmapReply> {
  public static final String PLUGIN_NAME = "dri3";

  public static final byte OPCODE = 3;

  private int pixmap;

  public XReplyFunction<BufferFromPixmapReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> BufferFromPixmapReply.readBufferFromPixmapReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static BufferFromPixmap readBufferFromPixmap(X11Input in) throws IOException {
    BufferFromPixmap.BufferFromPixmapBuilder javaBuilder = BufferFromPixmap.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int pixmap = in.readCard32();
    javaBuilder.pixmap(pixmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(pixmap);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class BufferFromPixmapBuilder {
    public int getSize() {
      return 8;
    }
  }
}
