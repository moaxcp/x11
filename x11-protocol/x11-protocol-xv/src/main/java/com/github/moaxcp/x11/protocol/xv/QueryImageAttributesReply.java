package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class QueryImageAttributesReply implements XReply {
  public static final String PLUGIN_NAME = "xv";

  private short sequenceNumber;

  private int dataSize;

  private short width;

  private short height;

  @NonNull
  private List<Integer> pitches;

  @NonNull
  private List<Integer> offsets;

  public static QueryImageAttributesReply readQueryImageAttributesReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    QueryImageAttributesReply.QueryImageAttributesReplyBuilder javaBuilder = QueryImageAttributesReply.builder();
    int length = in.readCard32();
    int numPlanes = in.readCard32();
    int dataSize = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    byte[] pad8 = in.readPad(12);
    List<Integer> pitches = in.readCard32((int) (Integer.toUnsignedLong(numPlanes)));
    List<Integer> offsets = in.readCard32((int) (Integer.toUnsignedLong(numPlanes)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.dataSize(dataSize);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.pitches(pitches);
    javaBuilder.offsets(offsets);
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
    int numPlanes = offsets.size();
    out.writeCard32(numPlanes);
    out.writeCard32(dataSize);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writePad(12);
    out.writeCard32(pitches);
    out.writeCard32(offsets);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * pitches.size() + 4 * offsets.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryImageAttributesReplyBuilder {
    public int getSize() {
      return 32 + 4 * pitches.size() + 4 * offsets.size();
    }
  }
}
