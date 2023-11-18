package com.github.moaxcp.x11client.protocol.dri3;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BufferFromPixmapReply implements XReply, Dri3Object {
  private byte nfd;

  private short sequenceNumber;

  private int size;

  private short width;

  private short height;

  private short stride;

  private byte depth;

  private byte bpp;

  private int pixmapFd;

  public static BufferFromPixmapReply readBufferFromPixmapReply(byte nfd, short sequenceNumber,
      X11Input in) throws IOException {
    BufferFromPixmapReply.BufferFromPixmapReplyBuilder javaBuilder = BufferFromPixmapReply.builder();
    int length = in.readCard32();
    int size = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    short stride = in.readCard16();
    byte depth = in.readCard8();
    byte bpp = in.readCard8();
    int pixmapFd = in.readInt32();
    byte[] pad11 = in.readPad(12);
    javaBuilder.nfd(nfd);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.size(size);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.stride(stride);
    javaBuilder.depth(depth);
    javaBuilder.bpp(bpp);
    javaBuilder.pixmapFd(pixmapFd);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(nfd);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(size);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(stride);
    out.writeCard8(depth);
    out.writeCard8(bpp);
    out.writeInt32(pixmapFd);
    out.writePad(12);
  }

  @Override
  public int getSize() {
    return 36;
  }

  public static class BufferFromPixmapReplyBuilder {
    public int getSize() {
      return 36;
    }
  }
}
