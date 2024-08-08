package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.Popcount;
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
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class DeviceLedInfo implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private short ledClass;

  private short ledID;

  private int physIndicators;

  private int state;

  @NonNull
  private IntList names;

  @NonNull
  private ImmutableList<IndicatorMap> maps;

  public static DeviceLedInfo readDeviceLedInfo(X11Input in) throws IOException {
    DeviceLedInfo.DeviceLedInfoBuilder javaBuilder = DeviceLedInfo.builder();
    short ledClass = in.readCard16();
    short ledID = in.readCard16();
    int namesPresent = in.readCard32();
    int mapsPresent = in.readCard32();
    int physIndicators = in.readCard32();
    int state = in.readCard32();
    IntList names = in.readCard32(Popcount.popcount(Integer.toUnsignedLong(namesPresent)));
    MutableList<IndicatorMap> maps = Lists.mutable.withInitialCapacity(Popcount.popcount(Integer.toUnsignedLong(mapsPresent)));
    for(int i = 0; i < Popcount.popcount(Integer.toUnsignedLong(mapsPresent)); i++) {
      maps.add(IndicatorMap.readIndicatorMap(in));
    }
    javaBuilder.ledClass(ledClass);
    javaBuilder.ledID(ledID);
    javaBuilder.physIndicators(physIndicators);
    javaBuilder.state(state);
    javaBuilder.names(names.toImmutable());
    javaBuilder.maps(maps.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(ledClass);
    out.writeCard16(ledID);
    int namesPresent = names.size();
    out.writeCard32(namesPresent);
    int mapsPresent = maps.size();
    out.writeCard32(mapsPresent);
    out.writeCard32(physIndicators);
    out.writeCard32(state);
    out.writeCard32(names);
    for(IndicatorMap t : maps) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 20 + 4 * names.size() + XObject.sizeOf(maps);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DeviceLedInfoBuilder {
    public DeviceLedInfo.DeviceLedInfoBuilder ledClass(LedClass ledClass) {
      this.ledClass = (short) ledClass.getValue();
      return this;
    }

    public DeviceLedInfo.DeviceLedInfoBuilder ledClass(short ledClass) {
      this.ledClass = ledClass;
      return this;
    }

    public int getSize() {
      return 20 + 4 * names.size() + XObject.sizeOf(maps);
    }
  }
}
