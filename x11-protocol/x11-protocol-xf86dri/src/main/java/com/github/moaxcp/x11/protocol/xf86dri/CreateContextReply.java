package com.github.moaxcp.x11.protocol.xf86dri;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateContextReply implements XReply {
  public static final String PLUGIN_NAME = "xf86dri";

  private short sequenceNumber;

  private int hwContext;

  public static CreateContextReply readCreateContextReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    CreateContextReply.CreateContextReplyBuilder javaBuilder = CreateContextReply.builder();
    int length = in.readCard32();
    int hwContext = in.readCard32();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.hwContext(hwContext);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(hwContext);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateContextReplyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
