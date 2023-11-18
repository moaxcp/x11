package com.github.moaxcp.x11client.protocol.xproto;

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
public class StoreColors implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 89;

  private int cmap;

  @NonNull
  private List<Coloritem> items;

  public byte getOpCode() {
    return OPCODE;
  }

  public static StoreColors readStoreColors(X11Input in) throws IOException {
    StoreColors.StoreColorsBuilder javaBuilder = StoreColors.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int cmap = in.readCard32();
    javaStart += 4;
    List<Coloritem> items = new ArrayList<>(length - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Coloritem baseObject = Coloritem.readColoritem(in);
      items.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.cmap(cmap);
    javaBuilder.items(items);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
    for(Coloritem t : items) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(items);
  }

  public static class StoreColorsBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(items);
    }
  }
}
