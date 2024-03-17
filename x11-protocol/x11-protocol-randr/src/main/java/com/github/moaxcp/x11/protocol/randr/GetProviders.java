package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetProviders implements TwoWayRequest<GetProvidersReply> {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 32;

  private int window;

  public XReplyFunction<GetProvidersReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetProvidersReply.readGetProvidersReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetProviders readGetProviders(X11Input in) throws IOException {
    GetProviders.GetProvidersBuilder javaBuilder = GetProviders.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetProvidersBuilder {
    public int getSize() {
      return 8;
    }
  }
}
