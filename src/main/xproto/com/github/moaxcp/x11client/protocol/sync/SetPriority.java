package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetPriority implements OneWayRequest, SyncObject {
  public static final byte OPCODE = 12;

  private int id;

  private int priority;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetPriority readSetPriority(X11Input in) throws IOException {
    SetPriority.SetPriorityBuilder javaBuilder = SetPriority.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int id = in.readCard32();
    int priority = in.readInt32();
    javaBuilder.id(id);
    javaBuilder.priority(priority);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(id);
    out.writeInt32(priority);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class SetPriorityBuilder {
    public int getSize() {
      return 12;
    }
  }
}
