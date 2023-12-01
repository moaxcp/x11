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
public class KeyName implements XStruct, XkbObject {
  @NonNull
  private List<Byte> name;

  public static KeyName readKeyName(X11Input in) throws IOException {
    KeyName.KeyNameBuilder javaBuilder = KeyName.builder();
    List<Byte> name = in.readChar(4);
    javaBuilder.name(name);
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

  public static class KeyNameBuilder {
    public int getSize() {
      return 0 + 1 * name.size();
    }
  }
}
