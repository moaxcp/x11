package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.xproto.Str;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ShortList;

@Value
@Builder
public class QueryFiltersReply implements XReply {
  public static final String PLUGIN_NAME = "render";

  private short sequenceNumber;

  @NonNull
  private ShortList aliases;

  @NonNull
  private ImmutableList<Str> filters;

  public static QueryFiltersReply readQueryFiltersReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryFiltersReply.QueryFiltersReplyBuilder javaBuilder = QueryFiltersReply.builder();
    int length = in.readCard32();
    int numAliases = in.readCard32();
    int numFilters = in.readCard32();
    byte[] pad6 = in.readPad(16);
    ShortList aliases = in.readCard16((int) (Integer.toUnsignedLong(numAliases)));
    MutableList<Str> filters = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numFilters)));
    for(int i = 0; i < Integer.toUnsignedLong(numFilters); i++) {
      filters.add(Str.readStr(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.aliases(aliases.toImmutable());
    javaBuilder.filters(filters.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryFiltersReplyBuilder {
    public int getSize() {
      return 32 + 2 * aliases.size() + XObject.sizeOf(filters);
    }
  }
}
