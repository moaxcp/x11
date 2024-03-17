package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ClientIdSpec implements XStruct {
  public static final String PLUGIN_NAME = "res";

  private int client;

  private int mask;

  public static ClientIdSpec readClientIdSpec(X11Input in) throws IOException {
    ClientIdSpec.ClientIdSpecBuilder javaBuilder = ClientIdSpec.builder();
    int client = in.readCard32();
    int mask = in.readCard32();
    javaBuilder.client(client);
    javaBuilder.mask(mask);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(client);
    out.writeCard32(mask);
  }

  public boolean isMaskEnabled(@NonNull ClientIdMask... maskEnums) {
    for(ClientIdMask m : maskEnums) {
      if(!m.isEnabled(mask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ClientIdSpecBuilder {
    public boolean isMaskEnabled(@NonNull ClientIdMask... maskEnums) {
      for(ClientIdMask m : maskEnums) {
        if(!m.isEnabled(mask)) {
          return false;
        }
      }
      return true;
    }

    public ClientIdSpec.ClientIdSpecBuilder maskEnable(ClientIdMask... maskEnums) {
      for(ClientIdMask m : maskEnums) {
        mask((int) m.enableFor(mask));
      }
      return this;
    }

    public ClientIdSpec.ClientIdSpecBuilder maskDisable(ClientIdMask... maskEnums) {
      for(ClientIdMask m : maskEnums) {
        mask((int) m.disableFor(mask));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }
  }
}
