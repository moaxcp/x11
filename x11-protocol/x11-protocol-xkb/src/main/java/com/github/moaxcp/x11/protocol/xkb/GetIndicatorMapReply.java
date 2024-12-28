package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.Popcount;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetIndicatorMapReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";

  private byte deviceID;

  private short sequenceNumber;

  private int realIndicators;

  private byte nIndicators;

  @NonNull
  private List<IndicatorMap> maps;

  public static GetIndicatorMapReply readGetIndicatorMapReply(byte deviceID, short sequenceNumber,
      X11Input in) throws IOException {
    GetIndicatorMapReply.GetIndicatorMapReplyBuilder javaBuilder = GetIndicatorMapReply.builder();
    int length = in.readCard32();
    int which = in.readCard32();
    int realIndicators = in.readCard32();
    byte nIndicators = in.readCard8();
    byte[] pad7 = in.readPad(15);
    List<IndicatorMap> maps = new ArrayList<>(Popcount.popcount(Integer.toUnsignedLong(which)));
    for(int i = 0; i < Popcount.popcount(Integer.toUnsignedLong(which)); i++) {
      maps.add(IndicatorMap.readIndicatorMap(in));
    }
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.realIndicators(realIndicators);
    javaBuilder.nIndicators(nIndicators);
    javaBuilder.maps(maps);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(deviceID);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    int which = maps.size();
    out.writeCard32(which);
    out.writeCard32(realIndicators);
    out.writeCard8(nIndicators);
    out.writePad(15);
    for(IndicatorMap t : maps) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(maps);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetIndicatorMapReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(maps);
    }
  }
}
