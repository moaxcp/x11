package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class QueryResourceBytes implements TwoWayRequest<QueryResourceBytesReply> {
  public static final String PLUGIN_NAME = "res";

  public static final byte OPCODE = 5;

  private int client;

  @NonNull
  private List<ResourceIdSpec> specs;

  public XReplyFunction<QueryResourceBytesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryResourceBytesReply.readQueryResourceBytesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryResourceBytes readQueryResourceBytes(X11Input in) throws IOException {
    QueryResourceBytes.QueryResourceBytesBuilder javaBuilder = QueryResourceBytes.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int client = in.readCard32();
    int numSpecs = in.readCard32();
    List<ResourceIdSpec> specs = new ArrayList<>((int) (Integer.toUnsignedLong(numSpecs)));
    for(int i = 0; i < Integer.toUnsignedLong(numSpecs); i++) {
      specs.add(ResourceIdSpec.readResourceIdSpec(in));
    }
    javaBuilder.client(client);
    javaBuilder.specs(specs);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(client);
    int numSpecs = specs.size();
    out.writeCard32(numSpecs);
    for(ResourceIdSpec t : specs) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(specs);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryResourceBytesBuilder {
    public int getSize() {
      return 12 + XObject.sizeOf(specs);
    }
  }
}
