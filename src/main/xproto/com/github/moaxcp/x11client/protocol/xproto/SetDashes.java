package com.github.moaxcp.x11client.protocol.xproto;

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
public class SetDashes implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 58;

  private int gc;

  private short dashOffset;

  @NonNull
  private List<Byte> dashes;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetDashes readSetDashes(X11Input in) throws IOException {
    SetDashes.SetDashesBuilder javaBuilder = SetDashes.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int gc = in.readCard32();
    short dashOffset = in.readCard16();
    short dashesLen = in.readCard16();
    List<Byte> dashes = in.readCard8(Short.toUnsignedInt(dashesLen));
    javaBuilder.gc(gc);
    javaBuilder.dashOffset(dashOffset);
    javaBuilder.dashes(dashes);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(gc);
    out.writeCard16(dashOffset);
    short dashesLen = (short) dashes.size();
    out.writeCard16(dashesLen);
    out.writeCard8(dashes);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 1 * dashes.size();
  }

  public static class SetDashesBuilder {
    public int getSize() {
      return 12 + 1 * dashes.size();
    }
  }
}
