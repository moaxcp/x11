package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class KeySymMap implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  @NonNull
  private ByteList ktIndex;

  private byte groupInfo;

  private byte width;

  @NonNull
  private IntList syms;

  public static KeySymMap readKeySymMap(X11Input in) throws IOException {
    KeySymMap.KeySymMapBuilder javaBuilder = KeySymMap.builder();
    ByteList ktIndex = in.readCard8(4);
    byte groupInfo = in.readCard8();
    byte width = in.readCard8();
    short nSyms = in.readCard16();
    IntList syms = in.readCard32(Short.toUnsignedInt(nSyms));
    javaBuilder.ktIndex(ktIndex.toImmutable());
    javaBuilder.groupInfo(groupInfo);
    javaBuilder.width(width);
    javaBuilder.syms(syms.toImmutable());
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class KeySymMapBuilder {
    public int getSize() {
      return 4 + 1 * ktIndex.size() + 4 * syms.size();
    }
  }
}
