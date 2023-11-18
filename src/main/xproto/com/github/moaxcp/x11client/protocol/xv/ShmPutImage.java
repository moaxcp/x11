package com.github.moaxcp.x11client.protocol.xv;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ShmPutImage implements OneWayRequest, XvObject {
  public static final byte OPCODE = 19;

  private int port;

  private int drawable;

  private int gc;

  private int shmseg;

  private int id;

  private int offset;

  private short srcX;

  private short srcY;

  private short srcW;

  private short srcH;

  private short drwX;

  private short drwY;

  private short drwW;

  private short drwH;

  private short width;

  private short height;

  private byte sendEvent;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ShmPutImage readShmPutImage(X11Input in) throws IOException {
    ShmPutImage.ShmPutImageBuilder javaBuilder = ShmPutImage.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int port = in.readCard32();
    int drawable = in.readCard32();
    int gc = in.readCard32();
    int shmseg = in.readCard32();
    int id = in.readCard32();
    int offset = in.readCard32();
    short srcX = in.readInt16();
    short srcY = in.readInt16();
    short srcW = in.readCard16();
    short srcH = in.readCard16();
    short drwX = in.readInt16();
    short drwY = in.readInt16();
    short drwW = in.readCard16();
    short drwH = in.readCard16();
    short width = in.readCard16();
    short height = in.readCard16();
    byte sendEvent = in.readCard8();
    byte[] pad20 = in.readPad(3);
    javaBuilder.port(port);
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.shmseg(shmseg);
    javaBuilder.id(id);
    javaBuilder.offset(offset);
    javaBuilder.srcX(srcX);
    javaBuilder.srcY(srcY);
    javaBuilder.srcW(srcW);
    javaBuilder.srcH(srcH);
    javaBuilder.drwX(drwX);
    javaBuilder.drwY(drwY);
    javaBuilder.drwW(drwW);
    javaBuilder.drwH(drwH);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.sendEvent(sendEvent);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(port);
    out.writeCard32(drawable);
    out.writeCard32(gc);
    out.writeCard32(shmseg);
    out.writeCard32(id);
    out.writeCard32(offset);
    out.writeInt16(srcX);
    out.writeInt16(srcY);
    out.writeCard16(srcW);
    out.writeCard16(srcH);
    out.writeInt16(drwX);
    out.writeInt16(drwY);
    out.writeCard16(drwW);
    out.writeCard16(drwH);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard8(sendEvent);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 52;
  }

  public static class ShmPutImageBuilder {
    public int getSize() {
      return 52;
    }
  }
}
