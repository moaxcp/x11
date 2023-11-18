package com.github.moaxcp.x11client.protocol.shm;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateSegment implements TwoWayRequest<CreateSegmentReply>, ShmObject {
  public static final byte OPCODE = 7;

  private int shmseg;

  private int size;

  private boolean readOnly;

  public XReplyFunction<CreateSegmentReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> CreateSegmentReply.readCreateSegmentReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateSegment readCreateSegment(X11Input in) throws IOException {
    CreateSegment.CreateSegmentBuilder javaBuilder = CreateSegment.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int shmseg = in.readCard32();
    int size = in.readCard32();
    boolean readOnly = in.readBool();
    byte[] pad6 = in.readPad(3);
    javaBuilder.shmseg(shmseg);
    javaBuilder.size(size);
    javaBuilder.readOnly(readOnly);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(shmseg);
    out.writeCard32(size);
    out.writeBool(readOnly);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class CreateSegmentBuilder {
    public int getSize() {
      return 16;
    }
  }
}
