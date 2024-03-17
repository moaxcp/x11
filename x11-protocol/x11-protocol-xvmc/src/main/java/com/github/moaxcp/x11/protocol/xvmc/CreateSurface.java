package com.github.moaxcp.x11.protocol.xvmc;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateSurface implements TwoWayRequest<CreateSurfaceReply> {
  public static final String PLUGIN_NAME = "xvmc";

  public static final byte OPCODE = 4;

  private int surfaceId;

  private int contextId;

  public XReplyFunction<CreateSurfaceReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> CreateSurfaceReply.readCreateSurfaceReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateSurface readCreateSurface(X11Input in) throws IOException {
    CreateSurface.CreateSurfaceBuilder javaBuilder = CreateSurface.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int surfaceId = in.readCard32();
    int contextId = in.readCard32();
    javaBuilder.surfaceId(surfaceId);
    javaBuilder.contextId(contextId);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(surfaceId);
    out.writeCard32(contextId);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateSurfaceBuilder {
    public int getSize() {
      return 12;
    }
  }
}
