package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GrabPort implements TwoWayRequest<GrabPortReply> {
  public static final String PLUGIN_NAME = "xv";

  public static final byte OPCODE = 3;

  private int port;

  private int time;

  public XReplyFunction<GrabPortReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GrabPortReply.readGrabPortReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GrabPort readGrabPort(X11Input in) throws IOException {
    GrabPort.GrabPortBuilder javaBuilder = GrabPort.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int port = in.readCard32();
    int time = in.readCard32();
    javaBuilder.port(port);
    javaBuilder.time(time);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(port);
    out.writeCard32(time);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GrabPortBuilder {
    public int getSize() {
      return 12;
    }
  }
}
