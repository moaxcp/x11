package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SelectBuffer implements OneWayRequest {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 106;

  private int contextTag;

  private int size;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectBuffer readSelectBuffer(X11Input in) throws IOException {
    SelectBuffer.SelectBufferBuilder javaBuilder = SelectBuffer.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int size = in.readInt32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.size(size);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeInt32(size);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SelectBufferBuilder {
    public int getSize() {
      return 12;
    }
  }
}
