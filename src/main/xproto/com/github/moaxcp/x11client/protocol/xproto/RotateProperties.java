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
public class RotateProperties implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 114;

  private int window;

  private short delta;

  @NonNull
  private List<Integer> atoms;

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
    List<Integer> atoms = in.readCard32(Short.toUnsignedInt(atomsLen));
    javaBuilder.window(window);
    javaBuilder.delta(delta);
    javaBuilder.atoms(atoms);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
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

  public static class RotatePropertiesBuilder {
    public int getSize() {
      return 12 + 4 * atoms.size();
    }
  }
}
