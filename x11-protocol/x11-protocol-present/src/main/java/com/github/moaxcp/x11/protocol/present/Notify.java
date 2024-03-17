package com.github.moaxcp.x11.protocol.present;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Notify implements XStruct {
  public static final String PLUGIN_NAME = "present";

  private int window;

  private int serial;

  public static Notify readNotify(X11Input in) throws IOException {
    Notify.NotifyBuilder javaBuilder = Notify.builder();
    int window = in.readCard32();
    int serial = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.serial(serial);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(window);
    out.writeCard32(serial);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class NotifyBuilder {
    public int getSize() {
      return 8;
    }
  }
}
