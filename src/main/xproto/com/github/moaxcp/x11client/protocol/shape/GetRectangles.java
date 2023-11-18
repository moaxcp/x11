package com.github.moaxcp.x11client.protocol.shape;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetRectangles implements TwoWayRequest<GetRectanglesReply>, ShapeObject {
  public static final byte OPCODE = 8;

  private int window;

  private byte sourceKind;

  public XReplyFunction<GetRectanglesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetRectanglesReply.readGetRectanglesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetRectangles readGetRectangles(X11Input in) throws IOException {
    GetRectangles.GetRectanglesBuilder javaBuilder = GetRectangles.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    byte sourceKind = in.readCard8();
    byte[] pad5 = in.readPad(3);
    javaBuilder.window(window);
    javaBuilder.sourceKind(sourceKind);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard8(sourceKind);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class GetRectanglesBuilder {
    public GetRectangles.GetRectanglesBuilder sourceKind(Sk sourceKind) {
      this.sourceKind = (byte) sourceKind.getValue();
      return this;
    }

    public GetRectangles.GetRectanglesBuilder sourceKind(byte sourceKind) {
      this.sourceKind = sourceKind;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
