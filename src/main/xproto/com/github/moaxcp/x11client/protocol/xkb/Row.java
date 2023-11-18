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
public class Row implements XStruct, XkbObject {
  private short top;

  private short left;

  private boolean vertical;

  @NonNull
  private List<Key> keys;

  public static Row readRow(X11Input in) throws IOException {
    Row.RowBuilder javaBuilder = Row.builder();
    short top = in.readInt16();
    short left = in.readInt16();
    byte nKeys = in.readCard8();
    boolean vertical = in.readBool();
    byte[] pad4 = in.readPad(2);
    List<Key> keys = new ArrayList<>(Byte.toUnsignedInt(nKeys));
    for(int i = 0; i < Byte.toUnsignedInt(nKeys); i++) {
      keys.add(Key.readKey(in));
    }
    javaBuilder.top(top);
    javaBuilder.left(left);
    javaBuilder.vertical(vertical);
    javaBuilder.keys(keys);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt16(top);
    out.writeInt16(left);
    byte nKeys = (byte) keys.size();
    out.writeCard8(nKeys);
    out.writeBool(vertical);
    out.writePad(2);
    for(Key t : keys) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(keys);
  }

  public static class RowBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(keys);
    }
  }
}
