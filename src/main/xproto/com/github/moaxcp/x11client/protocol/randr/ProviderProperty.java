package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProviderProperty implements NotifyDataUnion, XStruct, RandrObject {
  private int window;

  private int provider;

  private int atom;

  private int timestamp;

  private byte state;

  public static ProviderProperty readProviderProperty(X11Input in) throws IOException {
    ProviderProperty.ProviderPropertyBuilder javaBuilder = ProviderProperty.builder();
    int window = in.readCard32();
    int provider = in.readCard32();
    int atom = in.readCard32();
    int timestamp = in.readCard32();
    byte state = in.readCard8();
    byte[] pad5 = in.readPad(11);
    javaBuilder.window(window);
    javaBuilder.provider(provider);
    javaBuilder.atom(atom);
    javaBuilder.timestamp(timestamp);
    javaBuilder.state(state);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(window);
    out.writeCard32(provider);
    out.writeCard32(atom);
    out.writeCard32(timestamp);
    out.writeCard8(state);
    out.writePad(11);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public static class ProviderPropertyBuilder {
    public int getSize() {
      return 28;
    }
  }
}
