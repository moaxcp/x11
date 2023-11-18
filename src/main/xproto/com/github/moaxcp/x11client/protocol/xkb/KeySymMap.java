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
public class KeySymMap implements XStruct, XkbObject {
  @NonNull
  private List<Byte> ktIndex;

  private byte groupInfo;

  private byte width;

  @NonNull
  private List<Integer> syms;

  public static KeySymMap readKeySymMap(X11Input in) throws IOException {
    KeySymMap.KeySymMapBuilder javaBuilder = KeySymMap.builder();
    List<Byte> ktIndex = in.readCard8(4);
    byte groupInfo = in.readCard8();
    byte width = in.readCard8();
    short nSyms = in.readCard16();
    List<Integer> syms = in.readCard32(Short.toUnsignedInt(nSyms));
    javaBuilder.ktIndex(ktIndex);
    javaBuilder.groupInfo(groupInfo);
    javaBuilder.width(width);
    javaBuilder.syms(syms);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(ktIndex);
    out.writeCard8(groupInfo);
    out.writeCard8(width);
    short nSyms = (short) syms.size();
    out.writeCard16(nSyms);
    out.writeCard32(syms);
  }

  @Override
  public int getSize() {
    return 4 + 1 * ktIndex.size() + 4 * syms.size();
  }

  public static class KeySymMapBuilder {
    public int getSize() {
      return 4 + 1 * ktIndex.size() + 4 * syms.size();
    }
  }
}
