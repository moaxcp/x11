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
public class MakeCurrent implements TwoWayRequest<MakeCurrentReply> {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 5;

  private int drawable;

  private int context;

  private int oldContextTag;

  public XReplyFunction<MakeCurrentReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> MakeCurrentReply.readMakeCurrentReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static MakeCurrent readMakeCurrent(X11Input in) throws IOException {
    MakeCurrent.MakeCurrentBuilder javaBuilder = MakeCurrent.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    int context = in.readCard32();
    int oldContextTag = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.context(context);
    javaBuilder.oldContextTag(oldContextTag);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(context);
    out.writeCard32(oldContextTag);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class MakeCurrentBuilder {
    public int getSize() {
      return 16;
    }
  }
}
