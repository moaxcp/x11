package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class IsDirectReply implements XReply {
  public static final String PLUGIN_NAME = "glx";

  private short sequenceNumber;

  private boolean direct;

  public static IsDirectReply readIsDirectReply(byte pad1, short sequenceNumber, X11Input in) throws
      IOException {
    IsDirectReply.IsDirectReplyBuilder javaBuilder = IsDirectReply.builder();
    int length = in.readCard32();
    boolean direct = in.readBool();
    byte[] pad5 = in.readPad(23);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.direct(direct);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeBool(direct);
    out.writePad(23);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class IsDirectReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
