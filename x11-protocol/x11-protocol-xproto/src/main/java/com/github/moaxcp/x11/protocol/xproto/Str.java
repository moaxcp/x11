package com.github.moaxcp.x11.protocol.xproto;

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
public class Str implements XStruct {
  public static final String PLUGIN_NAME = "xproto";

  @NonNull
  private ByteList name;

  public static Str readStr(X11Input in) throws IOException {
    Str.StrBuilder javaBuilder = Str.builder();
    byte nameLen = in.readCard8();
    ByteList name = in.readChar(Byte.toUnsignedInt(nameLen));
    javaBuilder.name(name.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    byte nameLen = (byte) name.size();
    out.writeCard8(nameLen);
    out.writeChar(name);
  }

  @Override
  public int getSize() {
    return 1 + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class StrBuilder {
    public int getSize() {
      return 1 + 1 * name.size();
    }
  }
}
