package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Pictforminfo implements XStruct, RenderObject {
  private int id;

  private byte type;

  private byte depth;

  @NonNull
  private Directformat direct;

  private int colormap;

  public static Pictforminfo readPictforminfo(X11Input in) throws IOException {
    Pictforminfo.PictforminfoBuilder javaBuilder = Pictforminfo.builder();
    int id = in.readCard32();
    byte type = in.readCard8();
    byte depth = in.readCard8();
    byte[] pad3 = in.readPad(2);
    Directformat direct = Directformat.readDirectformat(in);
    int colormap = in.readCard32();
    javaBuilder.id(id);
    javaBuilder.type(type);
    javaBuilder.depth(depth);
    javaBuilder.direct(direct);
    javaBuilder.colormap(colormap);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(id);
    out.writeCard8(type);
    out.writeCard8(depth);
    out.writePad(2);
    direct.write(out);
    out.writeCard32(colormap);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public static class PictforminfoBuilder {
    public Pictforminfo.PictforminfoBuilder type(PictType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public Pictforminfo.PictforminfoBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 28;
    }
  }
}
