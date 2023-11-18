package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FreeLease implements OneWayRequest, RandrObject {
  public static final byte OPCODE = 46;

  private int lid;

  private byte terminate;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreeLease readFreeLease(X11Input in) throws IOException {
    FreeLease.FreeLeaseBuilder javaBuilder = FreeLease.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int lid = in.readCard32();
    byte terminate = in.readByte();
    javaBuilder.lid(lid);
    javaBuilder.terminate(terminate);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(lid);
    out.writeByte(terminate);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 9;
  }

  public static class FreeLeaseBuilder {
    public int getSize() {
      return 9;
    }
  }
}
