package com.github.moaxcp.x11.protocol.record;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetContext implements TwoWayRequest<GetContextReply> {
  public static final String PLUGIN_NAME = "record";

  public static final byte OPCODE = 4;

  private int context;

  public XReplyFunction<GetContextReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetContextReply.readGetContextReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetContext readGetContext(X11Input in) throws IOException {
    GetContext.GetContextBuilder javaBuilder = GetContext.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int context = in.readCard32();
    javaBuilder.context(context);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(context);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetContextBuilder {
    public int getSize() {
      return 8;
    }
  }
}
