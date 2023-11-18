package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetProviderInfo implements TwoWayRequest<GetProviderInfoReply>, RandrObject {
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
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int provider = in.readCard32();
    int configTimestamp = in.readCard32();
    javaBuilder.provider(provider);
    javaBuilder.configTimestamp(configTimestamp);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(provider);
    out.writeCard32(configTimestamp);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GetProviderInfoBuilder {
    public int getSize() {
      return 12;
    }
  }
}
