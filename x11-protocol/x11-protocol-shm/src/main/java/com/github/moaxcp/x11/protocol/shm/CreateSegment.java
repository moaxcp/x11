package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateSegment implements TwoWayRequest<CreateSegmentReply> {
  public static final String PLUGIN_NAME = "shm";

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
    byte majorOpcode = in.readCard8();
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
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateSegmentBuilder {
    public int getSize() {
      return 16;
    }
  }
}
