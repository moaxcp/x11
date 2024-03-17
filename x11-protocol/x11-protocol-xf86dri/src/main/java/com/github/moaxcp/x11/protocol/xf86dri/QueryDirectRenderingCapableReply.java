package com.github.moaxcp.x11.protocol.xf86dri;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryDirectRenderingCapableReply implements XReply {
  public static final String PLUGIN_NAME = "xf86dri";

  private short sequenceNumber;

  private boolean capable;

  public static QueryDirectRenderingCapableReply readQueryDirectRenderingCapableReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    QueryDirectRenderingCapableReply.QueryDirectRenderingCapableReplyBuilder javaBuilder = QueryDirectRenderingCapableReply.builder();
    int length = in.readCard32();
    boolean capable = in.readBool();
    in.readPad(23);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.capable(capable);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeBool(capable);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 9;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryDirectRenderingCapableReplyBuilder {
    public int getSize() {
      return 9;
    }
  }
}
