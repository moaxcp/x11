package com.github.moaxcp.x11.protocol.res;

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
public class ClientIdValue implements XStruct {
  public static final String PLUGIN_NAME = "res";

  @NonNull
  private ClientIdSpec spec;

  @NonNull
  private List<Integer> value;

  public static ClientIdValue readClientIdValue(X11Input in) throws IOException {
    ClientIdValue.ClientIdValueBuilder javaBuilder = ClientIdValue.builder();
    ClientIdSpec spec = ClientIdSpec.readClientIdSpec(in);
    int length = in.readCard32();
    List<Integer> value = in.readCard32((int) (Integer.toUnsignedLong(length) / 4));
    javaBuilder.spec(spec);
    javaBuilder.value(value);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    spec.write(out);
    int length = value.size();
    out.writeCard32(length);
    out.writeCard32(value);
  }

  @Override
  public int getSize() {
    return 12 + 4 * value.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ClientIdValueBuilder {
    public int getSize() {
      return 12 + 4 * value.size();
    }
  }
}
