package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DestroyPbuffer implements OneWayRequest {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 28;

  private int pbuffer;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DestroyPbuffer readDestroyPbuffer(X11Input in) throws IOException {
    DestroyPbuffer.DestroyPbufferBuilder javaBuilder = DestroyPbuffer.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int pbuffer = in.readCard32();
    javaBuilder.pbuffer(pbuffer);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(pbuffer);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DestroyPbufferBuilder {
    public int getSize() {
      return 8;
    }
  }
}
