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
public class GetWindowAttributes implements TwoWayRequest<GetWindowAttributesReply> {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 3;

  private int window;

  public XReplyFunction<GetWindowAttributesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetWindowAttributesReply.readGetWindowAttributesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetWindowAttributes readGetWindowAttributes(X11Input in) throws IOException {
    GetWindowAttributes.GetWindowAttributesBuilder javaBuilder = GetWindowAttributes.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetWindowAttributesBuilder {
    public int getSize() {
      return 8;
    }
  }
}
