package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProviderChange implements NotifyDataUnion, XStruct {
  public static final String PLUGIN_NAME = "randr";

  private int timestamp;

  private int window;

  private int provider;

  public static ProviderChange readProviderChange(X11Input in) throws IOException {
    ProviderChange.ProviderChangeBuilder javaBuilder = ProviderChange.builder();
    int timestamp = in.readCard32();
    int window = in.readCard32();
    int provider = in.readCard32();
    byte[] pad3 = in.readPad(16);
    javaBuilder.timestamp(timestamp);
    javaBuilder.window(window);
    javaBuilder.provider(provider);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(timestamp);
    out.writeCard32(window);
    out.writeCard32(provider);
    out.writePad(16);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ProviderChangeBuilder {
    public int getSize() {
      return 28;
    }
  }
}
