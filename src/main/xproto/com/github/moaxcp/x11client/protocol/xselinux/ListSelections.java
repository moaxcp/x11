package com.github.moaxcp.x11client.protocol.xselinux;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ListSelections implements TwoWayRequest<ListSelectionsReply>, XselinuxObject {
  public static final byte OPCODE = 21;

  public XReplyFunction<ListSelectionsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ListSelectionsReply.readListSelectionsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ListSelections readListSelections(X11Input in) throws IOException {
    ListSelections.ListSelectionsBuilder javaBuilder = ListSelections.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public static class ListSelectionsBuilder {
    public int getSize() {
      return 4;
    }
  }
}
