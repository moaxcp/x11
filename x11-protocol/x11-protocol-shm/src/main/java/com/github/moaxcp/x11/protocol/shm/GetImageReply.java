package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetImageReply implements XReply {
  public static final String PLUGIN_NAME = "shm";

  private byte depth;

  private short sequenceNumber;

  private int visual;

  private int size;

  public static GetImageReply readGetImageReply(byte depth, short sequenceNumber, X11Input in)
      throws IOException {
    GetImageReply.GetImageReplyBuilder javaBuilder = GetImageReply.builder();
    int length = in.readCard32();
    int visual = in.readCard32();
    int size = in.readCard32();
    in.readPad(16);
    javaBuilder.depth(depth);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.visual(visual);
    javaBuilder.size(size);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(depth);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(visual);
    out.writeCard32(size);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetImageReplyBuilder {
    public int getSize() {
      return 16;
    }
  }
}
