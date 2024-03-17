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
public class ListOutputProperties implements TwoWayRequest<ListOutputPropertiesReply> {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 10;

  private int output;

  public XReplyFunction<ListOutputPropertiesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ListOutputPropertiesReply.readListOutputPropertiesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ListOutputProperties readListOutputProperties(X11Input in) throws IOException {
    ListOutputProperties.ListOutputPropertiesBuilder javaBuilder = ListOutputProperties.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int output = in.readCard32();
    javaBuilder.output(output);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(output);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ListOutputPropertiesBuilder {
    public int getSize() {
      return 8;
    }
  }
}
