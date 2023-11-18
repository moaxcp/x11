package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class AwaitFence implements OneWayRequest, SyncObject {
  public static final byte OPCODE = 19;

  @NonNull
  private List<Integer> fenceList;

  public byte getOpCode() {
    return OPCODE;
  }

  public static AwaitFence readAwaitFence(X11Input in) throws IOException {
    AwaitFence.AwaitFenceBuilder javaBuilder = AwaitFence.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    List<Integer> fenceList = in.readCard32(javaStart - length);
    javaBuilder.fenceList(fenceList);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(fenceList);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 4 + 4 * fenceList.size();
  }

  public static class AwaitFenceBuilder {
    public int getSize() {
      return 4 + 4 * fenceList.size();
    }
  }
}
