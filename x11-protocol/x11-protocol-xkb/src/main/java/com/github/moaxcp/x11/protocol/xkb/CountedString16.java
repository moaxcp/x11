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
public class CountedString16 implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  @NonNull
  private ByteList string;

  @NonNull
  private ByteList alignmentPad;

  public static CountedString16 readCountedString16(X11Input in) throws IOException {
    CountedString16.CountedString16Builder javaBuilder = CountedString16.builder();
    short length = in.readCard16();
    ByteList string = in.readChar(Short.toUnsignedInt(length));
    ByteList alignmentPad = in.readVoid((Short.toUnsignedInt(length) + 5) & (~ (3)) - Short.toUnsignedInt(length) + 2);
    javaBuilder.string(string.toImmutable());
    javaBuilder.alignmentPad(alignmentPad.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    short length = (short) string.size();
    out.writeCard16(length);
    out.writeChar(string);
    out.writeVoid(alignmentPad);
  }

  @Override
  public int getSize() {
    return 2 + 1 * string.size() + 1 * alignmentPad.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CountedString16Builder {
    public int getSize() {
      return 2 + 1 * string.size() + 1 * alignmentPad.size();
    }
  }
}
