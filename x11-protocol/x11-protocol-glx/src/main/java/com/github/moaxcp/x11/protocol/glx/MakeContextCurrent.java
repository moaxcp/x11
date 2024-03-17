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
public class MakeContextCurrent implements TwoWayRequest<MakeContextCurrentReply> {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 26;

  private int oldContextTag;

  private int drawable;

  private int readDrawable;

  private int context;

  public XReplyFunction<MakeContextCurrentReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> MakeContextCurrentReply.readMakeContextCurrentReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static MakeContextCurrent readMakeContextCurrent(X11Input in) throws IOException {
    MakeContextCurrent.MakeContextCurrentBuilder javaBuilder = MakeContextCurrent.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int oldContextTag = in.readCard32();
    int drawable = in.readCard32();
    int readDrawable = in.readCard32();
    int context = in.readCard32();
    javaBuilder.oldContextTag(oldContextTag);
    javaBuilder.drawable(drawable);
    javaBuilder.readDrawable(readDrawable);
    javaBuilder.context(context);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(oldContextTag);
    out.writeCard32(drawable);
    out.writeCard32(readDrawable);
    out.writeCard32(context);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class MakeContextCurrentBuilder {
    public int getSize() {
      return 20;
    }
  }
}
