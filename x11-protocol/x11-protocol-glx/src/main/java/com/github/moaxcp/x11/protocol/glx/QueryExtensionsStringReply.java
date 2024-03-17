package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryExtensionsStringReply implements XReply {
  public static final String PLUGIN_NAME = "glx";

  private short sequenceNumber;

  private int n;

  public static QueryExtensionsStringReply readQueryExtensionsStringReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    QueryExtensionsStringReply.QueryExtensionsStringReplyBuilder javaBuilder = QueryExtensionsStringReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(4);
    int n = in.readCard32();
    byte[] pad6 = in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.n(n);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writePad(4);
    out.writeCard32(n);
    out.writePad(16);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryExtensionsStringReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
