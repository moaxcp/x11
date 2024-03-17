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
public class GetMonitors implements TwoWayRequest<GetMonitorsReply> {
  public static final String PLUGIN_NAME = "randr";

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
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    boolean getActive = in.readBool();
    javaBuilder.window(window);
    javaBuilder.getActive(getActive);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeBool(getActive);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 9;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetMonitorsBuilder {
    public int getSize() {
      return 9;
    }
  }
}
