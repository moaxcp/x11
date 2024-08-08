package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class GetImageReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private byte depth;

  private short sequenceNumber;

  private int visual;

  @NonNull
  private ByteList data;

  public static GetImageReply readGetImageReply(byte depth, short sequenceNumber, X11Input in)
      throws IOException {
    GetImageReply.GetImageReplyBuilder javaBuilder = GetImageReply.builder();
    int length = in.readCard32();
    int visual = in.readCard32();
    byte[] pad5 = in.readPad(20);
    ByteList data = in.readByte((int) (Integer.toUnsignedLong(length) * 4));
    javaBuilder.depth(depth);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.visual(visual);
    javaBuilder.data(data.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(depth);
    out.writeCard16(sequenceNumber);
    int length = data.size();
    out.writeCard32(getLength());
    out.writeCard32(visual);
    out.writePad(20);
    out.writeByte(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * data.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetImageReplyBuilder {
    public int getSize() {
      return 32 + 1 * data.size();
    }
  }
}
