package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetPriority implements TwoWayRequest<GetPriorityReply>, SyncObject {
  public static final byte OPCODE = 13;

  private int id;

  public XReplyFunction<GetPriorityReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetPriorityReply.readGetPriorityReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetPriority readGetPriority(X11Input in) throws IOException {
    GetPriority.GetPriorityBuilder javaBuilder = GetPriority.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int id = in.readCard32();
    javaBuilder.id(id);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(id);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class GetPriorityBuilder {
    public int getSize() {
      return 8;
    }
  }
}
