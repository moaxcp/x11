package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetOutputPrimary implements TwoWayRequest<GetOutputPrimaryReply> {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 31;

  private int window;

  public XReplyFunction<GetOutputPrimaryReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetOutputPrimaryReply.readGetOutputPrimaryReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetOutputPrimary readGetOutputPrimary(X11Input in) throws IOException {
    GetOutputPrimary.GetOutputPrimaryBuilder javaBuilder = GetOutputPrimary.builder();
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

  public static class GetOutputPrimaryBuilder {
    public int getSize() {
      return 8;
    }
  }
}
