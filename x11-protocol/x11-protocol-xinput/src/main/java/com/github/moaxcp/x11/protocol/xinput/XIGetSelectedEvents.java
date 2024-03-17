package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XIGetSelectedEvents implements TwoWayRequest<XIGetSelectedEventsReply> {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 60;

  private int window;

  public XReplyFunction<XIGetSelectedEventsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> XIGetSelectedEventsReply.readXIGetSelectedEventsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIGetSelectedEvents readXIGetSelectedEvents(X11Input in) throws IOException {
    XIGetSelectedEvents.XIGetSelectedEventsBuilder javaBuilder = XIGetSelectedEvents.builder();
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

  public static class XIGetSelectedEventsBuilder {
    public int getSize() {
      return 8;
    }
  }
}
