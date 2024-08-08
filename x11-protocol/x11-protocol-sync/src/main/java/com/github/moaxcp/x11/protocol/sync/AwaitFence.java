package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class AwaitFence implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";

  public static final byte OPCODE = 19;

  @NonNull
  private IntList fenceList;

  public byte getOpCode() {
    return OPCODE;
  }

  public static AwaitFence readAwaitFence(X11Input in) throws IOException {
    AwaitFence.AwaitFenceBuilder javaBuilder = AwaitFence.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    IntList fenceList = in.readCard32(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.fenceList(fenceList.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(fenceList);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 4 + 4 * fenceList.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class AwaitFenceBuilder {
    public int getSize() {
      return 4 + 4 * fenceList.size();
    }
  }
}
