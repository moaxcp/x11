package com.github.moaxcp.x11client.protocol.xvmc;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ListSubpictureTypes implements TwoWayRequest<ListSubpictureTypesReply>, XvmcObject {
  public static final byte OPCODE = 8;

  private int portId;

  private int surfaceId;

  public XReplyFunction<ListSubpictureTypesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ListSubpictureTypesReply.readListSubpictureTypesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ListSubpictureTypes readListSubpictureTypes(X11Input in) throws IOException {
    ListSubpictureTypes.ListSubpictureTypesBuilder javaBuilder = ListSubpictureTypes.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int portId = in.readCard32();
    int surfaceId = in.readCard32();
    javaBuilder.portId(portId);
    javaBuilder.surfaceId(surfaceId);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(portId);
    out.writeCard32(surfaceId);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class ListSubpictureTypesBuilder {
    public int getSize() {
      return 12;
    }
  }
}
