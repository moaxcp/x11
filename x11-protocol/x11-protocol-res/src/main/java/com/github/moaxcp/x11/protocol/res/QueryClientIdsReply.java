package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class QueryClientIdsReply implements XReply {
  public static final String PLUGIN_NAME = "res";

  private short sequenceNumber;

  @NonNull
  private ImmutableList<ClientIdValue> ids;

  public static QueryClientIdsReply readQueryClientIdsReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryClientIdsReply.QueryClientIdsReplyBuilder javaBuilder = QueryClientIdsReply.builder();
    int length = in.readCard32();
    int numIds = in.readCard32();
    byte[] pad5 = in.readPad(20);
    MutableList<ClientIdValue> ids = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numIds)));
    for(int i = 0; i < Integer.toUnsignedLong(numIds); i++) {
      ids.add(ClientIdValue.readClientIdValue(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.ids(ids.toImmutable());
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
    int numIds = ids.size();
    out.writeCard32(numIds);
    out.writePad(20);
    for(ClientIdValue t : ids) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(ids);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryClientIdsReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(ids);
    }
  }
}
