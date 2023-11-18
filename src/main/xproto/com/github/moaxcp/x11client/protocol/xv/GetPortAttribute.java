package com.github.moaxcp.x11client.protocol.xv;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetPortAttribute implements TwoWayRequest<GetPortAttributeReply>, XvObject {
  public static final byte OPCODE = 14;

  private int port;

  private int attribute;

  public XReplyFunction<GetPortAttributeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetPortAttributeReply.readGetPortAttributeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetPortAttribute readGetPortAttribute(X11Input in) throws IOException {
    GetPortAttribute.GetPortAttributeBuilder javaBuilder = GetPortAttribute.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int port = in.readCard32();
    int attribute = in.readCard32();
    javaBuilder.port(port);
    javaBuilder.attribute(attribute);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(port);
    out.writeCard32(attribute);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GetPortAttributeBuilder {
    public int getSize() {
      return 12;
    }
  }
}
