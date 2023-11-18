package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetMonitors implements TwoWayRequest<GetMonitorsReply>, RandrObject {
  public static final byte OPCODE = 42;

  private int window;

  private boolean getActive;

  public XReplyFunction<GetMonitorsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetMonitorsReply.readGetMonitorsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetMonitors readGetMonitors(X11Input in) throws IOException {
    GetMonitors.GetMonitorsBuilder javaBuilder = GetMonitors.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    boolean getActive = in.readBool();
    javaBuilder.window(window);
    javaBuilder.getActive(getActive);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeBool(getActive);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 9;
  }

  public static class GetMonitorsBuilder {
    public int getSize() {
      return 9;
    }
  }
}
