package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RenderMode implements TwoWayRequest<RenderModeReply> {
  public static final String PLUGIN_NAME = "glx";

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
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int mode = in.readCard32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.mode(mode);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(mode);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class RenderModeBuilder {
    public int getSize() {
      return 12;
    }
  }
}
