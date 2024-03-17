package com.github.moaxcp.x11.protocol.xtest;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetVersionReply implements XReply {
  public static final String PLUGIN_NAME = "xtest";

  private byte majorVersion;

  private short sequenceNumber;

  private short minorVersion;

  public static GetVersionReply readGetVersionReply(byte majorVersion, short sequenceNumber,
      X11Input in) throws IOException {
    GetVersionReply.GetVersionReplyBuilder javaBuilder = GetVersionReply.builder();
    int length = in.readCard32();
    short minorVersion = in.readCard16();
    in.readPad(22);
    javaBuilder.majorVersion(majorVersion);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.minorVersion(minorVersion);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(majorVersion);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(minorVersion);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 10;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetVersionReplyBuilder {
    public int getSize() {
      return 10;
    }
  }
}
