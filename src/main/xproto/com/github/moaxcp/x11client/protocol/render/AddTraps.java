package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class AddTraps implements OneWayRequest, RenderObject {
  public static final byte OPCODE = 32;

  private int picture;

  private short xOff;

  private short yOff;

  @NonNull
  private List<Trap> traps;

  public byte getOpCode() {
    return OPCODE;
  }

  public static AddTraps readAddTraps(X11Input in) throws IOException {
    AddTraps.AddTrapsBuilder javaBuilder = AddTraps.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int picture = in.readCard32();
    javaStart += 4;
    short xOff = in.readInt16();
    javaStart += 2;
    short yOff = in.readInt16();
    javaStart += 2;
    List<Trap> traps = new ArrayList<>(length - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Trap baseObject = Trap.readTrap(in);
      traps.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.picture(picture);
    javaBuilder.xOff(xOff);
    javaBuilder.yOff(yOff);
    javaBuilder.traps(traps);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
    out.writeInt16(xOff);
    out.writeInt16(yOff);
    for(Trap t : traps) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(traps);
  }

  public static class AddTrapsBuilder {
    public int getSize() {
      return 12 + XObject.sizeOf(traps);
    }
  }
}
