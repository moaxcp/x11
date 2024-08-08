package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class StoreColors implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 89;

  private int cmap;

  @NonNull
  private ImmutableList<Coloritem> items;

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
    MutableList<Coloritem> items = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Coloritem baseObject = Coloritem.readColoritem(in);
      items.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.cmap(cmap);
    javaBuilder.items(items.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class StoreColorsBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(items);
    }
  }
}
