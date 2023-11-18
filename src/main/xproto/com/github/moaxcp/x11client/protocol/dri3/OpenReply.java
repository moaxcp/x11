package com.github.moaxcp.x11client.protocol.dri3;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OpenReply implements XReply, Dri3Object {
  private byte nfd;

  private short sequenceNumber;

  private int deviceFd;

  public static OpenReply readOpenReply(byte nfd, short sequenceNumber, X11Input in) throws
      IOException {
    OpenReply.OpenReplyBuilder javaBuilder = OpenReply.builder();
    int length = in.readCard32();
    int deviceFd = in.readInt32();
    byte[] pad5 = in.readPad(24);
    javaBuilder.nfd(nfd);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.deviceFd(deviceFd);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(nfd);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeInt32(deviceFd);
    out.writePad(24);
  }

  @Override
  public int getSize() {
    return 36;
  }

  public static class OpenReplyBuilder {
    public int getSize() {
      return 36;
    }
  }
}
