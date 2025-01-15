package com.github.moaxcp.x11.x11client.api.image;

import com.github.moaxcp.x11.protocol.xproto.ImageFormat;
import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.api.list.primitive.ImmutableByteList;
import org.eclipse.collections.api.list.primitive.MutableByteList;
import org.eclipse.collections.impl.factory.primitive.ByteLists;

public class Bitmap {

  private final BitmapInfo info;
  private final short width;
  private final short height;
  private final byte leftPad;
  private final int lineByteCount;

  private ImmutableByteList data;

  public Bitmap(BitmapInfo info, short width, short height) {
    this(info, width, height, ByteLists.immutable.empty());
  }

  public Bitmap(BitmapInfo info, short width, short height, ByteList data) {
    this.info = info;
    this.width = width;
    this.height = height;

    int lineBitCount = width * info.getPixmapFormat().getBitsPerPixel();
    int scanlinePad = info.getPixmapFormat().getScanlinePad();

    // do we have to pad?
    int rem = lineBitCount % scanlinePad;

    // and if so, how much?
    int linePadCount = (lineBitCount / scanlinePad) + (rem == 0 ? 0 : 1);
    lineByteCount = (linePadCount * scanlinePad) / 8;

    this.leftPad = (byte) (getFormat() == ImageFormat.Z_PIXMAP ? 0 : lineByteCount * 8 - linePadCount);

    int dataBytes = Byte.toUnsignedInt(leftPad) + lineByteCount * height;
    if (data.size() < dataBytes) {
      var bytes = ByteLists.mutable.withAll(data);
      for (int i = data.size(); i < dataBytes; i++) {
        bytes.add((byte) 0);
      }
      this.data = bytes.toImmutable();
    } else if (data.size() > dataBytes) {
      var bytes = ByteLists.mutable.withAll(data);
      bytes.rejectWithIndex((b , i) -> i >= dataBytes);
    } else {
      this.data = data.toImmutable();
    }
  }

  public ImageFormat getFormat() {
    return ImageFormat.X_Y_BITMAP;
  }

  public BitmapInfo getInfo() {
    return info;
  }

  public short getWidth() {
    return width;
  }

  public short getHeight() {
    return height;
  }

  public byte getLeftPad() {
    return leftPad;
  }

  public ImmutableByteList getData() {
    return data;
  }

  public Editor editor() {
    return new Editor();
  }

  public class Editor {
    private final MutableByteList edited;

    private Editor() {
      edited = Bitmap.this.data.toList();
    }

    public Bitmap create() {
      return new Bitmap(info, width, height, edited.toImmutable());
    }
// leftPad = 217 xx = 416 unitIndex = 13 unitByteIndex = 0 bitIndex = 0 index = 5624
    public void set(int x, int y) {
      int xx = Byte.toUnsignedInt(leftPad) + x;
      int unitIndex = xx / Byte.toUnsignedInt(info.getBitmapFormatScanlineUnit());
      int unitByteIndex = (xx % Byte.toUnsignedInt(info.getBitmapFormatScanlineUnit())) / 8; // LSBFirst
      int bitIndex = xx % 8; // LeastSignificant
      int index = y * lineByteCount
          + unitIndex * Byte.toUnsignedInt(info.getUnitByteCount())
          + unitByteIndex;

      edited.set(index, (byte) (edited.get(index) | 1 << bitIndex));
    }
  }
}
