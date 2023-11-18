package com.github.moaxcp.x11client.protocol.shm;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PutImage implements OneWayRequest, ShmObject {
  public static final byte OPCODE = 3;

  private int drawable;

  private int gc;

  private short totalWidth;

  private short totalHeight;

  private short srcX;

  private short srcY;

  private short srcWidth;

  private short srcHeight;

  private short dstX;

  private short dstY;

  private byte depth;

  private byte format;

  private boolean sendEvent;

  private int shmseg;

  private int offset;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PutImage readPutImage(X11Input in) throws IOException {
    PutImage.PutImageBuilder javaBuilder = PutImage.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int drawable = in.readCard32();
    int gc = in.readCard32();
    short totalWidth = in.readCard16();
    short totalHeight = in.readCard16();
    short srcX = in.readCard16();
    short srcY = in.readCard16();
    short srcWidth = in.readCard16();
    short srcHeight = in.readCard16();
    short dstX = in.readInt16();
    short dstY = in.readInt16();
    byte depth = in.readCard8();
    byte format = in.readCard8();
    boolean sendEvent = in.readBool();
    byte[] pad16 = in.readPad(1);
    int shmseg = in.readCard32();
    int offset = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.totalWidth(totalWidth);
    javaBuilder.totalHeight(totalHeight);
    javaBuilder.srcX(srcX);
    javaBuilder.srcY(srcY);
    javaBuilder.srcWidth(srcWidth);
    javaBuilder.srcHeight(srcHeight);
    javaBuilder.dstX(dstX);
    javaBuilder.dstY(dstY);
    javaBuilder.depth(depth);
    javaBuilder.format(format);
    javaBuilder.sendEvent(sendEvent);
    javaBuilder.shmseg(shmseg);
    javaBuilder.offset(offset);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(gc);
    out.writeCard16(totalWidth);
    out.writeCard16(totalHeight);
    out.writeCard16(srcX);
    out.writeCard16(srcY);
    out.writeCard16(srcWidth);
    out.writeCard16(srcHeight);
    out.writeInt16(dstX);
    out.writeInt16(dstY);
    out.writeCard8(depth);
    out.writeCard8(format);
    out.writeBool(sendEvent);
    out.writePad(1);
    out.writeCard32(shmseg);
    out.writeCard32(offset);
  }

  @Override
  public int getSize() {
    return 40;
  }

  public static class PutImageBuilder {
    public int getSize() {
      return 40;
    }
  }
}
