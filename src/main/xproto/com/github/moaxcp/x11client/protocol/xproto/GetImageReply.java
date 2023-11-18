package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetImageReply implements XReply, XprotoObject {
  private byte depth;

  private short sequenceNumber;

  private int visual;

  @NonNull
  private List<Byte> data;

  public static GetImageReply readGetImageReply(byte depth, short sequenceNumber, X11Input in)
      throws IOException {
    GetImageReply.GetImageReplyBuilder javaBuilder = GetImageReply.builder();
    int length = in.readCard32();
    int visual = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<Byte> data = in.readByte((int) (Integer.toUnsignedLong(length) * 4));
    javaBuilder.depth(depth);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.visual(visual);
    javaBuilder.data(data);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
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

  public static class GetImageReplyBuilder {
    public int getSize() {
      return 32 + 1 * data.size();
    }
  }
}
