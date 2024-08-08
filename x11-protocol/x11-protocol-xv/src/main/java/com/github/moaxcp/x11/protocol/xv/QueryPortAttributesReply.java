package com.github.moaxcp.x11.protocol.xv;

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
public class QueryPortAttributesReply implements XReply {
  public static final String PLUGIN_NAME = "xv";

  private short sequenceNumber;

  private int textSize;

  @NonNull
  private ImmutableList<AttributeInfo> attributes;

  public static QueryPortAttributesReply readQueryPortAttributesReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    QueryPortAttributesReply.QueryPortAttributesReplyBuilder javaBuilder = QueryPortAttributesReply.builder();
    int length = in.readCard32();
    int numAttributes = in.readCard32();
    int textSize = in.readCard32();
    byte[] pad6 = in.readPad(16);
    MutableList<AttributeInfo> attributes = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numAttributes)));
    for(int i = 0; i < Integer.toUnsignedLong(numAttributes); i++) {
      attributes.add(AttributeInfo.readAttributeInfo(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.textSize(textSize);
    javaBuilder.attributes(attributes.toImmutable());
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
    int numAttributes = attributes.size();
    out.writeCard32(numAttributes);
    out.writeCard32(textSize);
    out.writePad(16);
    for(AttributeInfo t : attributes) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(attributes);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryPortAttributesReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(attributes);
    }
  }
}
