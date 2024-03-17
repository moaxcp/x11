package com.github.moaxcp.x11.protocol.dri3;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FDFromFenceReply implements XReply {
  public static final String PLUGIN_NAME = "dri3";

  private byte nfd;

  private short sequenceNumber;

  private int fenceFd;

  public static FDFromFenceReply readFDFromFenceReply(byte nfd, short sequenceNumber, X11Input in)
      throws IOException {
    FDFromFenceReply.FDFromFenceReplyBuilder javaBuilder = FDFromFenceReply.builder();
    int length = in.readCard32();
    int fenceFd = in.readInt32();
    byte[] pad5 = in.readPad(24);
    javaBuilder.nfd(nfd);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.fenceFd(fenceFd);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(nfd);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeInt32(fenceFd);
    out.writePad(24);
  }

  @Override
  public int getSize() {
    return 36;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class FDFromFenceReplyBuilder {
    public int getSize() {
      return 36;
    }
  }
}
