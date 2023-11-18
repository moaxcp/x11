package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DestroyMode implements OneWayRequest, RandrObject {
  public static final byte OPCODE = 17;

  private int mode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DestroyMode readDestroyMode(X11Input in) throws IOException {
    DestroyMode.DestroyModeBuilder javaBuilder = DestroyMode.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int mode = in.readCard32();
    javaBuilder.mode(mode);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(mode);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class DestroyModeBuilder {
    public int getSize() {
      return 8;
    }
  }
}
