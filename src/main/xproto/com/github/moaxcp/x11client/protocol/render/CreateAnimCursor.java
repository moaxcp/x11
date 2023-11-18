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
public class CreateAnimCursor implements OneWayRequest, RenderObject {
  public static final byte OPCODE = 31;

  private int cid;

  @NonNull
  private List<Animcursorelt> cursors;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateAnimCursor readCreateAnimCursor(X11Input in) throws IOException {
    CreateAnimCursor.CreateAnimCursorBuilder javaBuilder = CreateAnimCursor.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int cid = in.readCard32();
    javaStart += 4;
    List<Animcursorelt> cursors = new ArrayList<>(length - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Animcursorelt baseObject = Animcursorelt.readAnimcursorelt(in);
      cursors.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.cid(cid);
    javaBuilder.cursors(cursors);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cid);
    for(Animcursorelt t : cursors) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(cursors);
  }

  public static class CreateAnimCursorBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(cursors);
    }
  }
}
