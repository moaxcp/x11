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
public class CountedString16 implements XStruct, XkbObject {
  @NonNull
  private List<Byte> string;

  @NonNull
  private List<Byte> alignmentPad;

  public static CountedString16 readCountedString16(X11Input in) throws IOException {
    CountedString16.CountedString16Builder javaBuilder = CountedString16.builder();
    short length = in.readCard16();
    List<Byte> string = in.readChar(Short.toUnsignedInt(length));
    List<Byte> alignmentPad = in.readVoid((Short.toUnsignedInt(length) + 5) & (~ (3)) - Short.toUnsignedInt(length) + 2);
    javaBuilder.string(string);
    javaBuilder.alignmentPad(alignmentPad);
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

  public static class CountedString16Builder {
    public int getSize() {
      return 2 + 1 * string.size() + 1 * alignmentPad.size();
    }
  }
}
