package com.github.moaxcp.x11.protocol.res;

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
public class QueryResourceBytesReply implements XReply {
  public static final String PLUGIN_NAME = "res";

  private short sequenceNumber;

  @NonNull
  private List<ResourceSizeValue> sizes;

  public static QueryResourceBytesReply readQueryResourceBytesReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryResourceBytesReply.QueryResourceBytesReplyBuilder javaBuilder = QueryResourceBytesReply.builder();
    int length = in.readCard32();
    int numSizes = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<ResourceSizeValue> sizes = new ArrayList<>((int) (Integer.toUnsignedLong(numSizes)));
    for(int i = 0; i < Integer.toUnsignedLong(numSizes); i++) {
      sizes.add(ResourceSizeValue.readResourceSizeValue(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.sizes(sizes);
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
    int numSizes = sizes.size();
    out.writeCard32(numSizes);
    out.writePad(20);
    for(ResourceSizeValue t : sizes) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(sizes);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryResourceBytesReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(sizes);
    }
  }
}
