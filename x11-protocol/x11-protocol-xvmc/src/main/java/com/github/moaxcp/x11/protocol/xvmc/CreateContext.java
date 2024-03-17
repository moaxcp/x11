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
public class CreateContext implements TwoWayRequest<CreateContextReply> {
  public static final String PLUGIN_NAME = "xvmc";

  public static final byte OPCODE = 2;

  private int contextId;

  private int portId;

  private int surfaceId;

  private short width;

  private short height;

  private int flags;

  public XReplyFunction<CreateContextReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> CreateContextReply.readCreateContextReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateContext readCreateContext(X11Input in) throws IOException {
    CreateContext.CreateContextBuilder javaBuilder = CreateContext.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextId = in.readCard32();
    int portId = in.readCard32();
    int surfaceId = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    int flags = in.readCard32();
    javaBuilder.contextId(contextId);
    javaBuilder.portId(portId);
    javaBuilder.surfaceId(surfaceId);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.flags(flags);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(contextId);
    out.writeCard32(portId);
    out.writeCard32(surfaceId);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard32(flags);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateContextBuilder {
    public int getSize() {
      return 24;
    }
  }
}
