package com.github.moaxcp.x11.protocol.dpms;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetTimeouts implements OneWayRequest {
  public static final String PLUGIN_NAME = "dpms";

  public static final byte OPCODE = 3;

  private short standbyTimeout;

  private short suspendTimeout;

  private short offTimeout;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetTimeouts readSetTimeouts(X11Input in) throws IOException {
    SetTimeouts.SetTimeoutsBuilder javaBuilder = SetTimeouts.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short standbyTimeout = in.readCard16();
    short suspendTimeout = in.readCard16();
    short offTimeout = in.readCard16();
    javaBuilder.standbyTimeout(standbyTimeout);
    javaBuilder.suspendTimeout(suspendTimeout);
    javaBuilder.offTimeout(offTimeout);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(standbyTimeout);
    out.writeCard16(suspendTimeout);
    out.writeCard16(offTimeout);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 10;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetTimeoutsBuilder {
    public int getSize() {
      return 10;
    }
  }
}
