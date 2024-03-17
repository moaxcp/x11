package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DestroyMode implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 17;

  private int mode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DestroyMode readDestroyMode(X11Input in) throws IOException {
    DestroyMode.DestroyModeBuilder javaBuilder = DestroyMode.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int mode = in.readCard32();
    javaBuilder.mode(mode);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(mode);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DestroyModeBuilder {
    public int getSize() {
      return 8;
    }
  }
}
