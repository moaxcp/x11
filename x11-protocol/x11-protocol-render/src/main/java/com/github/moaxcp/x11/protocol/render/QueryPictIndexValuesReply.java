package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class QueryPictIndexValuesReply implements XReply {
  public static final String PLUGIN_NAME = "render";

  private short sequenceNumber;

  @NonNull
  private List<Indexvalue> values;

  public static QueryPictIndexValuesReply readQueryPictIndexValuesReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    QueryPictIndexValuesReply.QueryPictIndexValuesReplyBuilder javaBuilder = QueryPictIndexValuesReply.builder();
    int length = in.readCard32();
    int numValues = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<Indexvalue> values = new ArrayList<>((int) (Integer.toUnsignedLong(numValues)));
    for(int i = 0; i < Integer.toUnsignedLong(numValues); i++) {
      values.add(Indexvalue.readIndexvalue(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.values(values);
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
    int numValues = values.size();
    out.writeCard32(numValues);
    out.writePad(20);
    for(Indexvalue t : values) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(values);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryPictIndexValuesReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(values);
    }
  }
}
