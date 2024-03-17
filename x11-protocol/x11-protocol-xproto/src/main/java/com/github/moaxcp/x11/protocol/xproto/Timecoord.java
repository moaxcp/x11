package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Timecoord implements XStruct {
  public static final String PLUGIN_NAME = "xproto";

  private int time;

  private short x;

  private short y;

  public static Timecoord readTimecoord(X11Input in) throws IOException {
    Timecoord.TimecoordBuilder javaBuilder = Timecoord.builder();
    int time = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    javaBuilder.time(time);
    javaBuilder.x(x);
    javaBuilder.y(y);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(time);
    out.writeInt16(x);
    out.writeInt16(y);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class TimecoordBuilder {
    public int getSize() {
      return 8;
    }
  }
}
