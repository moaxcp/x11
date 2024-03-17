package com.github.moaxcp.x11.protocol.xtest;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CompareCursorReply implements XReply {
  public static final String PLUGIN_NAME = "xtest";

  private boolean same;

  private short sequenceNumber;

  public static CompareCursorReply readCompareCursorReply(byte same, short sequenceNumber,
      X11Input in) throws IOException {
    CompareCursorReply.CompareCursorReplyBuilder javaBuilder = CompareCursorReply.builder();
    int length = in.readCard32();
    in.readPad(24);
    javaBuilder.same(same > 0);
    javaBuilder.sequenceNumber(sequenceNumber);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeBool(same);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CompareCursorReplyBuilder {
    public int getSize() {
      return 8;
    }
  }
}
