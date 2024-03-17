package com.github.moaxcp.x11.protocol.xvmc;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DestroyContext implements OneWayRequest {
  public static final String PLUGIN_NAME = "xvmc";

  public static final byte OPCODE = 3;

  private int contextId;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DestroyContext readDestroyContext(X11Input in) throws IOException {
    DestroyContext.DestroyContextBuilder javaBuilder = DestroyContext.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextId = in.readCard32();
    javaBuilder.contextId(contextId);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(contextId);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DestroyContextBuilder {
    public int getSize() {
      return 8;
    }
  }
}
