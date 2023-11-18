package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class XIQueryVersionReply implements XReply, XinputObject {
  private short sequenceNumber;

  private short majorVersion;

  private short minorVersion;

  public static XIQueryVersionReply readXIQueryVersionReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    XIQueryVersionReply.XIQueryVersionReplyBuilder javaBuilder = XIQueryVersionReply.builder();
    int length = in.readCard32();
    short majorVersion = in.readCard16();
    short minorVersion = in.readCard16();
    byte[] pad6 = in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.majorVersion(majorVersion);
    javaBuilder.minorVersion(minorVersion);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(majorVersion);
    out.writeCard16(minorVersion);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class XIQueryVersionReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
