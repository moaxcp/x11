package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FeedbackBuffer implements OneWayRequest, GlxObject {
  public static final byte OPCODE = 105;

  private int contextTag;

  private int size;

  private int type;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FeedbackBuffer readFeedbackBuffer(X11Input in) throws IOException {
    FeedbackBuffer.FeedbackBufferBuilder javaBuilder = FeedbackBuffer.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int size = in.readInt32();
    int type = in.readInt32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.size(size);
    javaBuilder.type(type);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeInt32(size);
    out.writeInt32(type);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class FeedbackBufferBuilder {
    public int getSize() {
      return 16;
    }
  }
}
