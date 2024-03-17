package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetRectangles implements TwoWayRequest<GetRectanglesReply> {
  public static final String PLUGIN_NAME = "shape";

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
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    byte sourceKind = in.readCard8();
    byte[] pad5 = in.readPad(3);
    javaBuilder.window(window);
    javaBuilder.sourceKind(sourceKind);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard8(sourceKind);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
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
