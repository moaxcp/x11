package com.github.moaxcp.x11.protocol.render;

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
public class CreateAnimCursor implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";

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
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int cid = in.readCard32();
    javaStart += 4;
    List<Animcursorelt> cursors = new ArrayList<>(Short.toUnsignedInt(length) - javaStart);
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
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateAnimCursorBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(cursors);
    }
  }
}
