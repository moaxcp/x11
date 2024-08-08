package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class Row implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private short top;

  private short left;

  private boolean vertical;

  @NonNull
  private ImmutableList<Key> keys;

  public static Row readRow(X11Input in) throws IOException {
    Row.RowBuilder javaBuilder = Row.builder();
    short top = in.readInt16();
    short left = in.readInt16();
    byte nKeys = in.readCard8();
    boolean vertical = in.readBool();
    byte[] pad4 = in.readPad(2);
    MutableList<Key> keys = Lists.mutable.withInitialCapacity(Byte.toUnsignedInt(nKeys));
    for(int i = 0; i < Byte.toUnsignedInt(nKeys); i++) {
      keys.add(Key.readKey(in));
    }
    javaBuilder.top(top);
    javaBuilder.left(left);
    javaBuilder.vertical(vertical);
    javaBuilder.keys(keys.toImmutable());
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class RowBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(keys);
    }
  }
}
