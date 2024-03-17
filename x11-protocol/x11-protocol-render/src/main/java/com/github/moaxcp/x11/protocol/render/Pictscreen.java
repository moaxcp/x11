package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Pictscreen implements XStruct {
  public static final String PLUGIN_NAME = "render";

  private int fallback;

  @NonNull
  private List<Pictdepth> depths;

  public static Pictscreen readPictscreen(X11Input in) throws IOException {
    Pictscreen.PictscreenBuilder javaBuilder = Pictscreen.builder();
    int numDepths = in.readCard32();
    int fallback = in.readCard32();
    List<Pictdepth> depths = new ArrayList<>((int) (Integer.toUnsignedLong(numDepths)));
    for(int i = 0; i < Integer.toUnsignedLong(numDepths); i++) {
      depths.add(Pictdepth.readPictdepth(in));
    }
    javaBuilder.fallback(fallback);
    javaBuilder.depths(depths);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    int numDepths = depths.size();
    out.writeCard32(numDepths);
    out.writeCard32(fallback);
    for(Pictdepth t : depths) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(depths);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PictscreenBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(depths);
    }
  }
}
