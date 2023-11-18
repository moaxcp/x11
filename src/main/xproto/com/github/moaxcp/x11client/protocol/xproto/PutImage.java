package com.github.moaxcp.x11client.protocol.xproto;

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
public class PutImage implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 72;

  private byte format;

  private int drawable;

  private int gc;

  private short width;

  private short height;

  private short dstX;

  private short dstY;

  private byte leftPad;

  private byte depth;

  @NonNull
  private List<Byte> data;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PutImage readPutImage(X11Input in) throws IOException {
    PutImage.PutImageBuilder javaBuilder = PutImage.builder();
    int javaStart = 1;
    byte format = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int drawable = in.readCard32();
    javaStart += 4;
    int gc = in.readCard32();
    javaStart += 4;
    short width = in.readCard16();
    javaStart += 2;
    short height = in.readCard16();
    javaStart += 2;
    short dstX = in.readInt16();
    javaStart += 2;
    short dstY = in.readInt16();
    javaStart += 2;
    byte leftPad = in.readCard8();
    javaStart += 1;
    byte depth = in.readCard8();
    javaStart += 1;
    byte[] pad11 = in.readPad(2);
    javaStart += 2;
    List<Byte> data = in.readByte(javaStart - length);
    javaBuilder.format(format);
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.dstX(dstX);
    javaBuilder.dstY(dstY);
    javaBuilder.leftPad(leftPad);
    javaBuilder.depth(depth);
    javaBuilder.data(data);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeCard8(format);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(gc);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeInt16(dstX);
    out.writeInt16(dstY);
    out.writeCard8(leftPad);
    out.writeCard8(depth);
    out.writePad(2);
    out.writeByte(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 24 + 1 * data.size();
  }

  public static class PutImageBuilder {
    public PutImage.PutImageBuilder format(ImageFormat format) {
      this.format = (byte) format.getValue();
      return this;
    }

    public PutImage.PutImageBuilder format(byte format) {
      this.format = format;
      return this;
    }

    public int getSize() {
      return 24 + 1 * data.size();
    }
  }
}
