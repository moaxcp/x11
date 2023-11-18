package com.github.moaxcp.x11client.protocol.shm;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Detach implements OneWayRequest, ShmObject {
  public static final byte OPCODE = 2;

  private int shmseg;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Detach readDetach(X11Input in) throws IOException {
    Detach.DetachBuilder javaBuilder = Detach.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int shmseg = in.readCard32();
    javaBuilder.shmseg(shmseg);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(shmseg);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class DetachBuilder {
    public int getSize() {
      return 8;
    }
  }
}
