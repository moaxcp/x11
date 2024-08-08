package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class Key implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  @NonNull
  private ByteList name;

  private short gap;

  private byte shapeNdx;

  private byte colorNdx;

  public static Key readKey(X11Input in) throws IOException {
    Key.KeyBuilder javaBuilder = Key.builder();
    ByteList name = in.readChar(4);
    short gap = in.readInt16();
    byte shapeNdx = in.readCard8();
    byte colorNdx = in.readCard8();
    javaBuilder.name(name.toImmutable());
    javaBuilder.gap(gap);
    javaBuilder.shapeNdx(shapeNdx);
    javaBuilder.colorNdx(colorNdx);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeChar(name);
    out.writeInt16(gap);
    out.writeCard8(shapeNdx);
    out.writeCard8(colorNdx);
  }

  @Override
  public int getSize() {
    return 4 + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class KeyBuilder {
    public int getSize() {
      return 4 + 1 * name.size();
    }
  }
}
