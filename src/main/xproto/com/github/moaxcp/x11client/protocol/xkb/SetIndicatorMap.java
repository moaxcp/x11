package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.Popcount;
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
public class SetIndicatorMap implements OneWayRequest, XkbObject {
  public static final byte OPCODE = 14;

  private short deviceSpec;

  @NonNull
  private List<IndicatorMap> maps;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetIndicatorMap readSetIndicatorMap(X11Input in) throws IOException {
    SetIndicatorMap.SetIndicatorMapBuilder javaBuilder = SetIndicatorMap.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte[] pad4 = in.readPad(2);
    int which = in.readCard32();
    List<IndicatorMap> maps = new ArrayList<>(Popcount.popcount(Integer.toUnsignedLong(which)));
    for(int i = 0; i < Popcount.popcount(Integer.toUnsignedLong(which)); i++) {
      maps.add(IndicatorMap.readIndicatorMap(in));
    }
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.maps(maps);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writePad(2);
    int which = maps.size();
    out.writeCard32(which);
    for(IndicatorMap t : maps) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(maps);
  }

  public static class SetIndicatorMapBuilder {
    public int getSize() {
      return 12 + XObject.sizeOf(maps);
    }
  }
}
