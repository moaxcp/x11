package com.github.moaxcp.x11.protocol.xproto;

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
public class RotateProperties implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 114;

  private int window;

  private short delta;

  @NonNull
  private IntList atoms;

  public byte getOpCode() {
    return OPCODE;
  }

  public static RotateProperties readRotateProperties(X11Input in) throws IOException {
    RotateProperties.RotatePropertiesBuilder javaBuilder = RotateProperties.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    short atomsLen = in.readCard16();
    short delta = in.readInt16();
    IntList atoms = in.readCard32(Short.toUnsignedInt(atomsLen));
    javaBuilder.window(window);
    javaBuilder.delta(delta);
    javaBuilder.atoms(atoms.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    short atomsLen = (short) atoms.size();
    out.writeCard16(atomsLen);
    out.writeInt16(delta);
    out.writeCard32(atoms);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 4 * atoms.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class RotatePropertiesBuilder {
    public int getSize() {
      return 12 + 4 * atoms.size();
    }
  }
}
