package com.github.moaxcp.x11.protocol.xf86dri;

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
  public static final String PLUGIN_NAME = "xf86dri";

  public static final byte OPCODE = 5;

  private int screen;

  private int visual;

  private int context;

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
    int screen = in.readCard32();
    int visual = in.readCard32();
    int context = in.readCard32();
    javaBuilder.screen(screen);
    javaBuilder.visual(visual);
    javaBuilder.context(context);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(screen);
    out.writeCard32(visual);
    out.writeCard32(context);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateContextBuilder {
    public int getSize() {
      return 16;
    }
  }
}
