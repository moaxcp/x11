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
public class Open implements TwoWayRequest<OpenReply> {
  public static final String PLUGIN_NAME = "dri3";

  public static final byte OPCODE = 1;

  private int drawable;

  private int provider;

  public XReplyFunction<OpenReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> OpenReply.readOpenReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static Open readOpen(X11Input in) throws IOException {
    Open.OpenBuilder javaBuilder = Open.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    int provider = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.provider(provider);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(provider);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class OpenBuilder {
    public int getSize() {
      return 12;
    }
  }
}
