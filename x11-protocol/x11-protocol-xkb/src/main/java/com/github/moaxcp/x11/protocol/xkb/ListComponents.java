package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ListComponents implements TwoWayRequest<ListComponentsReply> {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte OPCODE = 22;

  private short deviceSpec;

  private short maxNames;

  public XReplyFunction<ListComponentsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ListComponentsReply.readListComponentsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ListComponents readListComponents(X11Input in) throws IOException {
    ListComponents.ListComponentsBuilder javaBuilder = ListComponents.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short maxNames = in.readCard16();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.maxNames(maxNames);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard16(maxNames);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ListComponentsBuilder {
    public int getSize() {
      return 8;
    }
  }
}
