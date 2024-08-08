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
public class KeyAlias implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  @NonNull
  private ByteList real;

  @NonNull
  private ByteList alias;

  public static KeyAlias readKeyAlias(X11Input in) throws IOException {
    KeyAlias.KeyAliasBuilder javaBuilder = KeyAlias.builder();
    ByteList real = in.readChar(4);
    ByteList alias = in.readChar(4);
    javaBuilder.real(real.toImmutable());
    javaBuilder.alias(alias.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeChar(real);
    out.writeChar(alias);
  }

  @Override
  public int getSize() {
    return 0 + 1 * real.size() + 1 * alias.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class KeyAliasBuilder {
    public int getSize() {
      return 0 + 1 * real.size() + 1 * alias.size();
    }
  }
}
