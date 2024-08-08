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
public class KeyName implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  @NonNull
  private ByteList name;

  public static KeyName readKeyName(X11Input in) throws IOException {
    KeyName.KeyNameBuilder javaBuilder = KeyName.builder();
    ByteList name = in.readChar(4);
    javaBuilder.name(name.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeChar(name);
  }

  @Override
  public int getSize() {
    return 0 + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class KeyNameBuilder {
    public int getSize() {
      return 0 + 1 * name.size();
    }
  }
}
