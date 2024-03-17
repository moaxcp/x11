package com.github.moaxcp.x11.protocol.xf86dri;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateDrawableReply implements XReply {
  public static final String PLUGIN_NAME = "xf86dri";

  private short sequenceNumber;

  private int hwDrawableHandle;

  public static CreateDrawableReply readCreateDrawableReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    CreateDrawableReply.CreateDrawableReplyBuilder javaBuilder = CreateDrawableReply.builder();
    int length = in.readCard32();
    int hwDrawableHandle = in.readCard32();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.hwDrawableHandle(hwDrawableHandle);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(hwDrawableHandle);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateDrawableReplyBuilder {
    public int getSize() {
      return 12;
    }
  }
}
