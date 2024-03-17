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
public class GetProviderInfo implements TwoWayRequest<GetProviderInfoReply> {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 33;

  private int provider;

  private int configTimestamp;

  public XReplyFunction<GetProviderInfoReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetProviderInfoReply.readGetProviderInfoReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetProviderInfo readGetProviderInfo(X11Input in) throws IOException {
    GetProviderInfo.GetProviderInfoBuilder javaBuilder = GetProviderInfo.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int provider = in.readCard32();
    int configTimestamp = in.readCard32();
    javaBuilder.provider(provider);
    javaBuilder.configTimestamp(configTimestamp);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(provider);
    out.writeCard32(configTimestamp);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetProviderInfoBuilder {
    public int getSize() {
      return 12;
    }
  }
}
