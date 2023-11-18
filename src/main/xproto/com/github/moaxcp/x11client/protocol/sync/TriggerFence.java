package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TriggerFence implements OneWayRequest, SyncObject {
  public static final byte OPCODE = 15;

  private int fence;

  public byte getOpCode() {
    return OPCODE;
  }

  public static TriggerFence readTriggerFence(X11Input in) throws IOException {
    TriggerFence.TriggerFenceBuilder javaBuilder = TriggerFence.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int fence = in.readCard32();
    javaBuilder.fence(fence);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(fence);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class TriggerFenceBuilder {
    public int getSize() {
      return 8;
    }
  }
}
