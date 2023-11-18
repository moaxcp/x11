package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class OverlayKey implements XStruct, XkbObject {
  @NonNull
  private List<Byte> over;

  @NonNull
  private List<Byte> under;

  public static OverlayKey readOverlayKey(X11Input in) throws IOException {
    OverlayKey.OverlayKeyBuilder javaBuilder = OverlayKey.builder();
    List<Byte> over = in.readChar(4);
    List<Byte> under = in.readChar(4);
    javaBuilder.over(over);
    javaBuilder.under(under);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeChar(over);
    out.writeChar(under);
  }

  @Override
  public int getSize() {
    return 0 + 1 * over.size() + 1 * under.size();
  }

  public static class OverlayKeyBuilder {
    public int getSize() {
      return 0 + 1 * over.size() + 1 * under.size();
    }
  }
}
