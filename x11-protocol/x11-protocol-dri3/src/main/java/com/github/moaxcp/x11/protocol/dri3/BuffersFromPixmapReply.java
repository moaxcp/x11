package com.github.moaxcp.x11.protocol.dri3;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class BuffersFromPixmapReply implements XReply {
  public static final String PLUGIN_NAME = "dri3";

  private short sequenceNumber;

  private short width;

  private short height;

  private long modifier;

  private byte depth;

  private byte bpp;

  @NonNull
  private List<Integer> strides;

  @NonNull
  private List<Integer> offsets;

  @NonNull
  private List<Integer> buffers;

  public static BuffersFromPixmapReply readBuffersFromPixmapReply(byte nfd, short sequenceNumber,
      X11Input in) throws IOException {
    BuffersFromPixmapReply.BuffersFromPixmapReplyBuilder javaBuilder = BuffersFromPixmapReply.builder();
    int length = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    byte[] pad6 = in.readPad(4);
    long modifier = in.readCard64();
    byte depth = in.readCard8();
    byte bpp = in.readCard8();
    byte[] pad10 = in.readPad(6);
    List<Integer> strides = in.readCard32(Byte.toUnsignedInt(nfd));
    List<Integer> offsets = in.readCard32(Byte.toUnsignedInt(nfd));
    List<Integer> buffers = in.readFd(Byte.toUnsignedInt(nfd));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.modifier(modifier);
    javaBuilder.depth(depth);
    javaBuilder.bpp(bpp);
    javaBuilder.strides(strides);
    javaBuilder.offsets(offsets);
    javaBuilder.buffers(buffers);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    byte nfd = (byte) buffers.size();
    out.writeCard8(nfd);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(width);
    out.writeCard16(height);
    out.writePad(4);
    out.writeCard64(modifier);
    out.writeCard8(depth);
    out.writeCard8(bpp);
    out.writePad(6);
    out.writeCard32(strides);
    out.writeCard32(offsets);
    out.writeFd(buffers);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * strides.size() + 4 * offsets.size() + 4 * buffers.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class BuffersFromPixmapReplyBuilder {
    public int getSize() {
      return 32 + 4 * strides.size() + 4 * offsets.size() + 4 * buffers.size();
    }
  }
}
