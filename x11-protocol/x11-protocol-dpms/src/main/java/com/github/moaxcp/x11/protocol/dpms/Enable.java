package com.github.moaxcp.x11.protocol.dpms;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Enable implements OneWayRequest {
  public static final String PLUGIN_NAME = "dpms";

  public static final byte OPCODE = 4;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Enable readEnable(X11Input in) throws IOException {
    Enable.EnableBuilder javaBuilder = Enable.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class EnableBuilder {
    public int getSize() {
      return 4;
    }
  }
}
