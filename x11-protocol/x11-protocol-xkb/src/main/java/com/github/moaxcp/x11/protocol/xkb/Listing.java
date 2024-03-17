package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Listing implements XStruct {
  public static final String PLUGIN_NAME = "xkb";

  private short flags;

  @NonNull
  private List<Byte> string;

  public static Listing readListing(X11Input in) throws IOException {
    Listing.ListingBuilder javaBuilder = Listing.builder();
    short flags = in.readCard16();
    short length = in.readCard16();
    List<Byte> string = in.readChar(Short.toUnsignedInt(length));
    in.readPadAlign(2, Short.toUnsignedInt(length));
    javaBuilder.flags(flags);
    javaBuilder.string(string);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(flags);
    short length = (short) string.size();
    out.writeCard16(length);
    out.writeChar(string);
    out.writePadAlign(2, Short.toUnsignedInt(length));
  }

  @Override
  public int getSize() {
    return 4 + 1 * string.size() + XObject.getSizeForPadAlign(2, 1 * string.size());
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ListingBuilder {
    public int getSize() {
      return 4 + 1 * string.size() + XObject.getSizeForPadAlign(2, 1 * string.size());
    }
  }
}
