package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class QueryClientIds implements TwoWayRequest<QueryClientIdsReply> {
  public static final String PLUGIN_NAME = "res";

  public static final byte OPCODE = 4;

  @NonNull
  private ImmutableList<ClientIdSpec> specs;

  public XReplyFunction<QueryClientIdsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryClientIdsReply.readQueryClientIdsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryClientIds readQueryClientIds(X11Input in) throws IOException {
    QueryClientIds.QueryClientIdsBuilder javaBuilder = QueryClientIds.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int numSpecs = in.readCard32();
    MutableList<ClientIdSpec> specs = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numSpecs)));
    for(int i = 0; i < Integer.toUnsignedLong(numSpecs); i++) {
      specs.add(ClientIdSpec.readClientIdSpec(in));
    }
    javaBuilder.specs(specs.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    int numSpecs = specs.size();
    out.writeCard32(numSpecs);
    for(ClientIdSpec t : specs) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(specs);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryClientIdsBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(specs);
    }
  }
}
