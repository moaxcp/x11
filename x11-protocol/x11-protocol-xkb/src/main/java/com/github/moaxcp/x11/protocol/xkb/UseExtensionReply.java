package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UseExtensionReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";

  private boolean supported;

  private short sequenceNumber;

  private short serverMajor;

  private short serverMinor;

  public static UseExtensionReply readUseExtensionReply(byte supported, short sequenceNumber,
      X11Input in) throws IOException {
    UseExtensionReply.UseExtensionReplyBuilder javaBuilder = UseExtensionReply.builder();
    int length = in.readCard32();
    short serverMajor = in.readCard16();
    short serverMinor = in.readCard16();
    byte[] pad6 = in.readPad(20);
    javaBuilder.supported(supported > 0);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.serverMajor(serverMajor);
    javaBuilder.serverMinor(serverMinor);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeBool(supported);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(serverMajor);
    out.writeCard16(serverMinor);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class UseExtensionReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
