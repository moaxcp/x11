package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Detach implements OneWayRequest {
  public static final String PLUGIN_NAME = "shm";

  public static final byte OPCODE = 2;

  private int shmseg;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Detach readDetach(X11Input in) throws IOException {
    Detach.DetachBuilder javaBuilder = Detach.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int shmseg = in.readCard32();
    javaBuilder.shmseg(shmseg);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(shmseg);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DetachBuilder {
    public int getSize() {
      return 8;
    }
  }
}
