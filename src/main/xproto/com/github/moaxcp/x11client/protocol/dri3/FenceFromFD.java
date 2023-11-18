package com.github.moaxcp.x11client.protocol.dri3;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FenceFromFD implements OneWayRequest, Dri3Object {
  public static final byte OPCODE = 4;

  private int drawable;

  private int fence;

  private boolean initiallyTriggered;

  private int fenceFd;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FenceFromFD readFenceFromFD(X11Input in) throws IOException {
    FenceFromFD.FenceFromFDBuilder javaBuilder = FenceFromFD.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int drawable = in.readCard32();
    int fence = in.readCard32();
    boolean initiallyTriggered = in.readBool();
    byte[] pad6 = in.readPad(3);
    int fenceFd = in.readInt32();
    javaBuilder.drawable(drawable);
    javaBuilder.fence(fence);
    javaBuilder.initiallyTriggered(initiallyTriggered);
    javaBuilder.fenceFd(fenceFd);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(fence);
    out.writeBool(initiallyTriggered);
    out.writePad(3);
    out.writeInt32(fenceFd);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public static class FenceFromFDBuilder {
    public int getSize() {
      return 20;
    }
  }
}
