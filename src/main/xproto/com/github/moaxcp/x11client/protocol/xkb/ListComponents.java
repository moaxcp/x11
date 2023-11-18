package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ListComponents implements TwoWayRequest<ListComponentsReply>, XkbObject {
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
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short maxNames = in.readCard16();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.maxNames(maxNames);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard16(maxNames);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class ListComponentsBuilder {
    public int getSize() {
      return 8;
    }
  }
}
