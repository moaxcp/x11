package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XReply;
import com.github.moaxcp.x11client.protocol.xproto.Str;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class QueryFiltersReply implements XReply, RenderObject {
  private short sequenceNumber;

  @NonNull
  private List<Short> aliases;

  @NonNull
  private List<Str> filters;

  public static QueryFiltersReply readQueryFiltersReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryFiltersReply.QueryFiltersReplyBuilder javaBuilder = QueryFiltersReply.builder();
    int length = in.readCard32();
    int numAliases = in.readCard32();
    int numFilters = in.readCard32();
    byte[] pad6 = in.readPad(16);
    List<Short> aliases = in.readCard16((int) (Integer.toUnsignedLong(numAliases)));
    List<Str> filters = new ArrayList<>((int) (Integer.toUnsignedLong(numFilters)));
    for(int i = 0; i < Integer.toUnsignedLong(numFilters); i++) {
      filters.add(Str.readStr(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.aliases(aliases);
    javaBuilder.filters(filters);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    int numAliases = aliases.size();
    out.writeCard32(numAliases);
    int numFilters = filters.size();
    out.writeCard32(numFilters);
    out.writePad(16);
    out.writeCard16(aliases);
    for(Str t : filters) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 2 * aliases.size() + XObject.sizeOf(filters);
  }

  public static class QueryFiltersReplyBuilder {
    public int getSize() {
      return 32 + 2 * aliases.size() + XObject.sizeOf(filters);
    }
  }
}
