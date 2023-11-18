package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Overlay implements XStruct, XkbObject {
  private int name;

  @NonNull
  private List<OverlayRow> rows;

  public static Overlay readOverlay(X11Input in) throws IOException {
    Overlay.OverlayBuilder javaBuilder = Overlay.builder();
    int name = in.readCard32();
    byte nRows = in.readCard8();
    byte[] pad2 = in.readPad(3);
    List<OverlayRow> rows = new ArrayList<>(Byte.toUnsignedInt(nRows));
    for(int i = 0; i < Byte.toUnsignedInt(nRows); i++) {
      rows.add(OverlayRow.readOverlayRow(in));
    }
    javaBuilder.name(name);
    javaBuilder.rows(rows);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(name);
    byte nRows = (byte) rows.size();
    out.writeCard8(nRows);
    out.writePad(3);
    for(OverlayRow t : rows) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(rows);
  }

  public static class OverlayBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(rows);
    }
  }
}
