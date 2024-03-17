package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FeedbackBuffer implements OneWayRequest {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 105;

  private int contextTag;

  private int size;

  private int type;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FeedbackBuffer readFeedbackBuffer(X11Input in) throws IOException {
    FeedbackBuffer.FeedbackBufferBuilder javaBuilder = FeedbackBuffer.builder();
    byte majorOpcode = in.readCard8();
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
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeInt32(size);
    out.writeInt32(type);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class FeedbackBufferBuilder {
    public int getSize() {
      return 16;
    }
  }
}
