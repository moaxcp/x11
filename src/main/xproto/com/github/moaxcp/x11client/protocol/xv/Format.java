package com.github.moaxcp.x11client.protocol.xv;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Format implements XStruct, XvObject {
  private int visual;

  private byte depth;

  public static Format readFormat(X11Input in) throws IOException {
    Format.FormatBuilder javaBuilder = Format.builder();
    int visual = in.readCard32();
    byte depth = in.readCard8();
    byte[] pad2 = in.readPad(3);
    javaBuilder.visual(visual);
    javaBuilder.depth(depth);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(visual);
    out.writeCard8(depth);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class FormatBuilder {
    public int getSize() {
      return 8;
    }
  }
}
