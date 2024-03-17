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
public class ListProviderProperties implements TwoWayRequest<ListProviderPropertiesReply> {
  public static final String PLUGIN_NAME = "randr";

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
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int provider = in.readCard32();
    javaBuilder.provider(provider);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(provider);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ListProviderPropertiesBuilder {
    public int getSize() {
      return 8;
    }
  }
}
