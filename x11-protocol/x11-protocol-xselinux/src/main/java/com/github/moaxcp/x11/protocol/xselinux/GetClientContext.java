package com.github.moaxcp.x11.protocol.xselinux;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetClientContext implements TwoWayRequest<GetClientContextReply> {
  public static final String PLUGIN_NAME = "xselinux";

  public static final byte OPCODE = 22;

  private int resource;

  public XReplyFunction<GetClientContextReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetClientContextReply.readGetClientContextReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetClientContext readGetClientContext(X11Input in) throws IOException {
    GetClientContext.GetClientContextBuilder javaBuilder = GetClientContext.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int resource = in.readCard32();
    javaBuilder.resource(resource);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(resource);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetClientContextBuilder {
    public int getSize() {
      return 8;
    }
  }
}
