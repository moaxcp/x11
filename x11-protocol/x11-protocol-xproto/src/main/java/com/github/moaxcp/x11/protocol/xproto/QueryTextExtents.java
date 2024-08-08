package com.github.moaxcp.x11.protocol.xproto;

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
public class QueryTextExtents implements TwoWayRequest<QueryTextExtentsReply> {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 48;

  private int font;

  @NonNull
  private ImmutableList<Char2b> string;

  public XReplyFunction<QueryTextExtentsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryTextExtentsReply.readQueryTextExtentsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryTextExtents readQueryTextExtents(X11Input in) throws IOException {
    QueryTextExtents.QueryTextExtentsBuilder javaBuilder = QueryTextExtents.builder();
    int javaStart = 1;
    boolean oddLength = in.readBool();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int font = in.readCard32();
    javaStart += 4;
    MutableList<Char2b> string = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Char2b baseObject = Char2b.readChar2b(in);
      string.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.font(font);
    javaBuilder.string(string.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeBool(getOddLength());
    out.writeCard16((short) getLength());
    out.writeCard32(font);
    for(Char2b t : string) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  public boolean getOddLength() {
    return ((string.size()) & (1)) > 0;
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(string);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryTextExtentsBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(string);
    }
  }
}
