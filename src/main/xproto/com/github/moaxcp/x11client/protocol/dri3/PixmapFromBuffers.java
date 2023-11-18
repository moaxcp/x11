package com.github.moaxcp.x11client.protocol.dri3;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PixmapFromBuffers implements OneWayRequest, Dri3Object {
  public static final byte OPCODE = 7;

  private int pixmap;

  private int window;

  private short width;

  private short height;

  private int stride0;

  private int offset0;

  private int stride1;

  private int offset1;

  private int stride2;

  private int offset2;

  private int stride3;

  private int offset3;

  private byte depth;

  private byte bpp;

  private long modifier;

  @NonNull
  private List<Integer> buffers;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PixmapFromBuffers readPixmapFromBuffers(X11Input in) throws IOException {
    PixmapFromBuffers.PixmapFromBuffersBuilder javaBuilder = PixmapFromBuffers.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int pixmap = in.readCard32();
    int window = in.readCard32();
    byte numBuffers = in.readCard8();
    byte[] pad6 = in.readPad(3);
    short width = in.readCard16();
    short height = in.readCard16();
    int stride0 = in.readCard32();
    int offset0 = in.readCard32();
    int stride1 = in.readCard32();
    int offset1 = in.readCard32();
    int stride2 = in.readCard32();
    int offset2 = in.readCard32();
    int stride3 = in.readCard32();
    int offset3 = in.readCard32();
    byte depth = in.readCard8();
    byte bpp = in.readCard8();
    byte[] pad19 = in.readPad(2);
    long modifier = in.readCard64();
    List<Integer> buffers = in.readFd(Byte.toUnsignedInt(numBuffers));
    javaBuilder.pixmap(pixmap);
    javaBuilder.window(window);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.stride0(stride0);
    javaBuilder.offset0(offset0);
    javaBuilder.stride1(stride1);
    javaBuilder.offset1(offset1);
    javaBuilder.stride2(stride2);
    javaBuilder.offset2(offset2);
    javaBuilder.stride3(stride3);
    javaBuilder.offset3(offset3);
    javaBuilder.depth(depth);
    javaBuilder.bpp(bpp);
    javaBuilder.modifier(modifier);
    javaBuilder.buffers(buffers);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(pixmap);
    out.writeCard32(window);
    byte numBuffers = (byte) buffers.size();
    out.writeCard8(numBuffers);
    out.writePad(3);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard32(stride0);
    out.writeCard32(offset0);
    out.writeCard32(stride1);
    out.writeCard32(offset1);
    out.writeCard32(stride2);
    out.writeCard32(offset2);
    out.writeCard32(stride3);
    out.writeCard32(offset3);
    out.writeCard8(depth);
    out.writeCard8(bpp);
    out.writePad(2);
    out.writeCard64(modifier);
    out.writeFd(buffers);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 64 + 4 * buffers.size();
  }

  public static class PixmapFromBuffersBuilder {
    public int getSize() {
      return 64 + 4 * buffers.size();
    }
  }
}
