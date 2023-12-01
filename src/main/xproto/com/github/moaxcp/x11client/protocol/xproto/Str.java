package com.github.moaxcp.x11client.protocol.xproto;

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
public class Str implements XStruct, XprotoObject {
  @NonNull
  private List<Byte> name;

  public static Str readStr(X11Input in) throws IOException {
    Str.StrBuilder javaBuilder = Str.builder();
    byte nameLen = in.readCard8();
    List<Byte> name = in.readChar(Byte.toUnsignedInt(nameLen));
    javaBuilder.name(name);
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

  public static class StrBuilder {
    public int getSize() {
      return 1 + 1 * name.size();
    }
  }
}
