package com.github.moaxcp.x11.protocol.glx;

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
public class GetTexImageReply implements XReply {
  public static final String PLUGIN_NAME = "glx";

  private short sequenceNumber;

  private int width;

  private int height;

  private int depth;

  @NonNull
  private ByteList data;

  public static GetTexImageReply readGetTexImageReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    GetTexImageReply.GetTexImageReplyBuilder javaBuilder = GetTexImageReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(8);
    int width = in.readInt32();
    int height = in.readInt32();
    int depth = in.readInt32();
    byte[] pad8 = in.readPad(4);
    ByteList data = in.readByte((int) (Integer.toUnsignedLong(length) * 4));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.depth(depth);
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
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    int length = data.size();
    out.writeCard32(getLength());
    out.writePad(8);
    out.writeInt32(width);
    out.writeInt32(height);
    out.writeInt32(depth);
    out.writePad(4);
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

  public static class GetTexImageReplyBuilder {
    public int getSize() {
      return 32 + 1 * data.size();
    }
  }
}
