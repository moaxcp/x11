package com.github.moaxcp.x11.protocol.xevie;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Start implements TwoWayRequest<StartReply> {
  public static final String PLUGIN_NAME = "xevie";

  public static final byte OPCODE = 1;

  private int screen;

  public XReplyFunction<StartReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> StartReply.readStartReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static Start readStart(X11Input in) throws IOException {
    Start.StartBuilder javaBuilder = Start.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int screen = in.readCard32();
    javaBuilder.screen(screen);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(screen);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class StartBuilder {
    public int getSize() {
      return 8;
    }
  }
}
