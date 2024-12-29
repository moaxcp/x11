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
public class OverlayRow implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private byte rowUnder;

  @NonNull
  private ImmutableList<OverlayKey> keys;

  public static OverlayRow readOverlayRow(X11Input in) throws IOException {
    OverlayRow.OverlayRowBuilder javaBuilder = OverlayRow.builder();
    byte rowUnder = in.readCard8();
    byte nKeys = in.readCard8();
    byte[] pad2 = in.readPad(2);
    MutableList<OverlayKey> keys = Lists.mutable.withInitialCapacity(Byte.toUnsignedInt(nKeys));
    for(int i = 0; i < Byte.toUnsignedInt(nKeys); i++) {
      keys.add(OverlayKey.readOverlayKey(in));
    }
    javaBuilder.rowUnder(rowUnder);
    javaBuilder.keys(keys.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(rowUnder);
    byte nKeys = (byte) keys.size();
    out.writeCard8(nKeys);
    out.writePad(2);
    for(OverlayKey t : keys) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 4 + XObject.sizeOf(keys);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class OverlayRowBuilder {
    public int getSize() {
      return 4 + XObject.sizeOf(keys);
    }
  }
}
