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
public class ListProviderProperties implements TwoWayRequest<ListProviderPropertiesReply>, RandrObject {
  public static final byte OPCODE = 36;

  private int provider;

  public XReplyFunction<ListProviderPropertiesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ListProviderPropertiesReply.readListProviderPropertiesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ListProviderProperties readListProviderProperties(X11Input in) throws IOException {
    ListProviderProperties.ListProviderPropertiesBuilder javaBuilder = ListProviderProperties.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int provider = in.readCard32();
    javaBuilder.provider(provider);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(provider);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class ListProviderPropertiesBuilder {
    public int getSize() {
      return 8;
    }
  }
}
