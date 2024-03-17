package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class XIBarrierReleasePointer implements OneWayRequest {
  public static final String PLUGIN_NAME = "xinput";

  public static final byte OPCODE = 61;

  @NonNull
  private List<BarrierReleasePointerInfo> barriers;

  public byte getOpCode() {
    return OPCODE;
  }

  public static XIBarrierReleasePointer readXIBarrierReleasePointer(X11Input in) throws
      IOException {
    XIBarrierReleasePointer.XIBarrierReleasePointerBuilder javaBuilder = XIBarrierReleasePointer.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int numBarriers = in.readCard32();
    List<BarrierReleasePointerInfo> barriers = new ArrayList<>((int) (Integer.toUnsignedLong(numBarriers)));
    for(int i = 0; i < Integer.toUnsignedLong(numBarriers); i++) {
      barriers.add(BarrierReleasePointerInfo.readBarrierReleasePointerInfo(in));
    }
    javaBuilder.barriers(barriers);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    int numBarriers = barriers.size();
    out.writeCard32(numBarriers);
    for(BarrierReleasePointerInfo t : barriers) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(barriers);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XIBarrierReleasePointerBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(barriers);
    }
  }
}
