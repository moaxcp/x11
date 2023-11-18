package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateFence implements OneWayRequest, SyncObject {
  public static final byte OPCODE = 14;

  private int drawable;

  private int fence;

  private boolean initiallyTriggered;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateFence readCreateFence(X11Input in) throws IOException {
    CreateFence.CreateFenceBuilder javaBuilder = CreateFence.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int drawable = in.readCard32();
    int fence = in.readCard32();
    boolean initiallyTriggered = in.readBool();
    javaBuilder.drawable(drawable);
    javaBuilder.fence(fence);
    javaBuilder.initiallyTriggered(initiallyTriggered);
    in.readPadAlign(javaBuilder.getSize());
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
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 13;
  }

  public static class CreateFenceBuilder {
    public int getSize() {
      return 13;
    }
  }
}
