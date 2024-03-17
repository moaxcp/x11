package com.github.moaxcp.x11.protocol.xselinux;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetWindowContext implements TwoWayRequest<GetWindowContextReply> {
  public static final String PLUGIN_NAME = "xselinux";

  public static final byte OPCODE = 7;

  private int window;

  public XReplyFunction<GetWindowContextReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetWindowContextReply.readGetWindowContextReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetWindowContext readGetWindowContext(X11Input in) throws IOException {
    GetWindowContext.GetWindowContextBuilder javaBuilder = GetWindowContext.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public static class GetWindowContextBuilder {
    public int getSize() {
      return 8;
    }
  }
}
