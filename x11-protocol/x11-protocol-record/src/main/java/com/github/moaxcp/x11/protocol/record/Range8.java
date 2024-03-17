package com.github.moaxcp.x11.protocol.record;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Range8 implements XStruct {
  public static final String PLUGIN_NAME = "record";

  private byte first;

  private byte last;

  public static Range8 readRange8(X11Input in) throws IOException {
    Range8.Range8Builder javaBuilder = Range8.builder();
    byte first = in.readCard8();
    byte last = in.readCard8();
    javaBuilder.first(first);
    javaBuilder.last(last);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(first);
    out.writeCard8(last);
  }

  @Override
  public int getSize() {
    return 2;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class Range8Builder {
    public int getSize() {
      return 2;
    }
  }
}
