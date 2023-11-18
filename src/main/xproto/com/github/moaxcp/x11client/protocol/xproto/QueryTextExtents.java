package com.github.moaxcp.x11client.protocol.xproto;

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
public class QueryTextExtents implements TwoWayRequest<QueryTextExtentsReply>, XprotoObject {
  public static final byte OPCODE = 48;

  private int font;

  @NonNull
  private List<Char2b> string;

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
    List<Char2b> string = new ArrayList<>(length - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Char2b baseObject = Char2b.readChar2b(in);
      string.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.font(font);
    javaBuilder.string(string);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
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

  public static class QueryTextExtentsBuilder {
    public int getSize() {
      return 8 + XObject.sizeOf(string);
    }
  }
}
