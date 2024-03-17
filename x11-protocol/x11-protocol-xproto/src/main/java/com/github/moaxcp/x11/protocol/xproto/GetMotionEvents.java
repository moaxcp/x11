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
public class GetMotionEvents implements TwoWayRequest<GetMotionEventsReply> {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 39;

  private int window;

  private int start;

  private int stop;

  public XReplyFunction<GetMotionEventsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetMotionEventsReply.readGetMotionEventsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetMotionEvents readGetMotionEvents(X11Input in) throws IOException {
    GetMotionEvents.GetMotionEventsBuilder javaBuilder = GetMotionEvents.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    int start = in.readCard32();
    int stop = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.start(start);
    javaBuilder.stop(stop);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(start);
    out.writeCard32(stop);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetMotionEventsBuilder {
    public int getSize() {
      return 16;
    }
  }
}
