package com.github.moaxcp.x11client.protocol.present;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Notify implements XStruct, PresentObject {
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

  public static class NotifyBuilder {
    public int getSize() {
      return 8;
    }
  }
}
