package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FreeGC implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 60;

  private int gc;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreeGC readFreeGC(X11Input in) throws IOException {
    FreeGC.FreeGCBuilder javaBuilder = FreeGC.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int gc = in.readCard32();
    javaBuilder.gc(gc);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(gc);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class FreeGCBuilder {
    public int getSize() {
      return 8;
    }
  }
}
