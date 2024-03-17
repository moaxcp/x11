package com.github.moaxcp.x11.protocol.xevie;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SelectInputReply implements XReply {
  public static final String PLUGIN_NAME = "xevie";

  private short sequenceNumber;

  public static SelectInputReply readSelectInputReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    SelectInputReply.SelectInputReplyBuilder javaBuilder = SelectInputReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(24);
    javaBuilder.sequenceNumber(sequenceNumber);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writePad(24);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SelectInputReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
