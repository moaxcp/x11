package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class SetDashes implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 58;

  private int gc;

  private short dashOffset;

  @NonNull
  private ByteList dashes;

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
    ByteList dashes = in.readCard8(Short.toUnsignedInt(dashesLen));
    javaBuilder.gc(gc);
    javaBuilder.dashOffset(dashOffset);
    javaBuilder.dashes(dashes.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetDashesBuilder {
    public int getSize() {
      return 12 + 1 * dashes.size();
    }
  }
}
