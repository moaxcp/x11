package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateSegmentReply implements XReply {
  public static final String PLUGIN_NAME = "shm";

  private byte nfd;

  private short sequenceNumber;

  private int shmFd;

  public static CreateSegmentReply readCreateSegmentReply(byte nfd, short sequenceNumber,
      X11Input in) throws IOException {
    CreateSegmentReply.CreateSegmentReplyBuilder javaBuilder = CreateSegmentReply.builder();
    int length = in.readCard32();
    int shmFd = in.readInt32();
    byte[] pad5 = in.readPad(24);
    javaBuilder.nfd(nfd);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.shmFd(shmFd);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(nfd);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeInt32(shmFd);
    out.writePad(24);
  }

  @Override
  public int getSize() {
    return 36;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateSegmentReplyBuilder {
    public int getSize() {
      return 36;
    }
  }
}
