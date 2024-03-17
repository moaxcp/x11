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
public class GetInputFocus implements TwoWayRequest<GetInputFocusReply> {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 43;

  public XReplyFunction<GetInputFocusReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetInputFocusReply.readGetInputFocusReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetInputFocus readGetInputFocus(X11Input in) throws IOException {
    GetInputFocus.GetInputFocusBuilder javaBuilder = GetInputFocus.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetInputFocusBuilder {
    public int getSize() {
      return 4;
    }
  }
}
