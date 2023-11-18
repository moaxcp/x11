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
public class ListOutputProperties implements TwoWayRequest<ListOutputPropertiesReply>, RandrObject {
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
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int output = in.readCard32();
    javaBuilder.output(output);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(output);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class ListOutputPropertiesBuilder {
    public int getSize() {
      return 8;
    }
  }
}
