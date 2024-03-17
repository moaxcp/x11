package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetGeometry implements TwoWayRequest<GetGeometryReply> {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 14;

  private int drawable;

  public XReplyFunction<GetGeometryReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetGeometryReply.readGetGeometryReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetGeometry readGetGeometry(X11Input in) throws IOException {
    GetGeometry.GetGeometryBuilder javaBuilder = GetGeometry.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int drawable = in.readCard32();
    javaBuilder.drawable(drawable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetGeometryBuilder {
    public int getSize() {
      return 8;
    }
  }
}
