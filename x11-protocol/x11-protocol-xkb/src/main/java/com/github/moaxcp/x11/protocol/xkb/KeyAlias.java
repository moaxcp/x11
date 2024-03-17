package com.github.moaxcp.x11.protocol.xkb;

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
public class KeyAlias implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  @NonNull
  private List<Byte> real;

  @NonNull
  private List<Byte> alias;

  public static KeyAlias readKeyAlias(X11Input in) throws IOException {
    KeyAlias.KeyAliasBuilder javaBuilder = KeyAlias.builder();
    List<Byte> real = in.readChar(4);
    List<Byte> alias = in.readChar(4);
    javaBuilder.real(real);
    javaBuilder.alias(alias);
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
