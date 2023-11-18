package com.github.moaxcp.x11client.protocol.res;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class QueryClientIds implements TwoWayRequest<QueryClientIdsReply>, ResObject {
  public static final byte OPCODE = 4;

  @NonNull
  private List<ClientIdSpec> specs;

  public XReplyFunction<QueryClientIdsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryClientIdsReply.readQueryClientIdsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryClientIds readQueryClientIds(X11Input in) throws IOException {
    QueryClientIds.QueryClientIdsBuilder javaBuilder = QueryClientIds.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int numSpecs = in.readCard32();
    List<ClientIdSpec> specs = new ArrayList<>((int) (Integer.toUnsignedLong(numSpecs)));
    for(int i = 0; i < Integer.toUnsignedLong(numSpecs); i++) {
      specs.add(ClientIdSpec.readClientIdSpec(in));
    }
    javaBuilder.specs(specs);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
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

  public static class QueryClientIdsBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(specs);
    }
  }
}
