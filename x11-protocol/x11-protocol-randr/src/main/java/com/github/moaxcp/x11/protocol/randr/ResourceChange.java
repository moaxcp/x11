package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResourceChange implements NotifyDataUnion, XStruct {
  public static final String PLUGIN_NAME = "randr";

  private int timestamp;

  private int window;

  public static ResourceChange readResourceChange(X11Input in) throws IOException {
    ResourceChange.ResourceChangeBuilder javaBuilder = ResourceChange.builder();
    int timestamp = in.readCard32();
    int window = in.readCard32();
    byte[] pad2 = in.readPad(20);
    javaBuilder.timestamp(timestamp);
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(timestamp);
    out.writeCard32(window);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ResourceChangeBuilder {
    public int getSize() {
      return 28;
    }
  }
}
