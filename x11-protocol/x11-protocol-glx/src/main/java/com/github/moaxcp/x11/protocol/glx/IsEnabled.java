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
public class IsEnabled implements TwoWayRequest<IsEnabledReply> {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = (byte) 140;

  private int contextTag;

  private int capability;

  public XReplyFunction<IsEnabledReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> IsEnabledReply.readIsEnabledReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static IsEnabled readIsEnabled(X11Input in) throws IOException {
    IsEnabled.IsEnabledBuilder javaBuilder = IsEnabled.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int capability = in.readCard32();
    javaBuilder.contextTag(contextTag);
    javaBuilder.capability(capability);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard32(capability);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class IsEnabledBuilder {
    public int getSize() {
      return 12;
    }
  }
}
