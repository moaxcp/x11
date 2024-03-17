package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetControls implements TwoWayRequest<GetControlsReply> {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte OPCODE = 6;

  private short deviceSpec;

  public XReplyFunction<GetControlsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetControlsReply.readGetControlsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetControls readGetControls(X11Input in) throws IOException {
    GetControls.GetControlsBuilder javaBuilder = GetControls.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte[] pad4 = in.readPad(2);
    javaBuilder.deviceSpec(deviceSpec);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetControlsBuilder {
    public int getSize() {
      return 8;
    }
  }
}
