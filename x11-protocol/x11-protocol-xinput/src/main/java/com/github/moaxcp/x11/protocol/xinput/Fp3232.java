package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Fp3232 implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private int integral;

  private int frac;

  public static Fp3232 readFp3232(X11Input in) throws IOException {
    Fp3232.Fp3232Builder javaBuilder = Fp3232.builder();
    int integral = in.readInt32();
    int frac = in.readCard32();
    javaBuilder.integral(integral);
    javaBuilder.frac(frac);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt32(integral);
    out.writeCard32(frac);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class Fp3232Builder {
    public int getSize() {
      return 8;
    }
  }
}
