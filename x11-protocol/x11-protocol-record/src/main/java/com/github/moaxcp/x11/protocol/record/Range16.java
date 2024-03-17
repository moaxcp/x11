package com.github.moaxcp.x11.protocol.record;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Range16 implements XStruct {
  public static final String PLUGIN_NAME = "record";

  private short first;

  private short last;

  public static Range16 readRange16(X11Input in) throws IOException {
    Range16.Range16Builder javaBuilder = Range16.builder();
    short first = in.readCard16();
    short last = in.readCard16();
    javaBuilder.first(first);
    javaBuilder.last(last);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(first);
    out.writeCard16(last);
  }

  @Override
  public int getSize() {
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class Range16Builder {
    public int getSize() {
      return 4;
    }
  }
}
