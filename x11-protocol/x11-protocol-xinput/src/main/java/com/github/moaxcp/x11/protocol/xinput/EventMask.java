package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class EventMask implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private short deviceid;

  @NonNull
  private List<Integer> mask;

  public static EventMask readEventMask(X11Input in) throws IOException {
    EventMask.EventMaskBuilder javaBuilder = EventMask.builder();
    short deviceid = in.readCard16();
    short maskLen = in.readCard16();
    List<Integer> mask = in.readCard32(Short.toUnsignedInt(maskLen));
    javaBuilder.deviceid(deviceid);
    javaBuilder.mask(mask);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(deviceid);
    short maskLen = (short) mask.size();
    out.writeCard16(maskLen);
    out.writeCard32(mask);
  }

  @Override
  public int getSize() {
    return 4 + 4 * mask.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class EventMaskBuilder {
    public int getSize() {
      return 4 + 4 * mask.size();
    }
  }
}
