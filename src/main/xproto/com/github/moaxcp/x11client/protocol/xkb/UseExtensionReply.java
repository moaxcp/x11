package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UseExtensionReply implements XReply, XkbObject {
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

  public static class UseExtensionReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
