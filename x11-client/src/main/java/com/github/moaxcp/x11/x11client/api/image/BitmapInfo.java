package com.github.moaxcp.x11.x11client.api.image;

import com.github.moaxcp.x11.protocol.xproto.Format;

public class BitmapInfo {

  private final Format pixmapFormat;

  private final byte bitmapFormatBitOrder;

  private final byte bitmapFormatScanlineUnit;

  private final byte bitmapFormatScanlinePad;

  private final byte unitByteCount;

  public BitmapInfo(Format pixmapFormat, byte bitmapFormatBitOrder, byte bitmapFormatScanlineUnit, byte bitmapFormatScanlinePad) {
    this.pixmapFormat = pixmapFormat;
    this.bitmapFormatBitOrder = bitmapFormatBitOrder;
    this.bitmapFormatScanlineUnit = bitmapFormatScanlineUnit;
    this.bitmapFormatScanlinePad = bitmapFormatScanlinePad;
    unitByteCount = (byte) (bitmapFormatScanlineUnit / 8);
  }

  public Format getPixmapFormat() {
    return pixmapFormat;
  }

  public byte getBitmapFormatBitOrder() {
    return bitmapFormatBitOrder;
  }

  public byte getBitmapFormatScanlineUnit() {
    return bitmapFormatScanlineUnit;
  }

  public byte getBitmapFormatScanlinePad() {
    return bitmapFormatScanlinePad;
  }

  public byte getUnitByteCount() {
    return unitByteCount;
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BitmapInfo that)) return false;

    return bitmapFormatBitOrder == that.bitmapFormatBitOrder && bitmapFormatScanlineUnit == that.bitmapFormatScanlineUnit && bitmapFormatScanlinePad == that.bitmapFormatScanlinePad;
  }

  @Override
  public int hashCode() {
    int result = bitmapFormatBitOrder;
    result = 31 * result + bitmapFormatScanlineUnit;
    result = 31 * result + bitmapFormatScanlinePad;
    return result;
  }

  @Override
  public String toString() {
    return "BitmapInfo{" +
        "bitmapFormatBitOrder=" + bitmapFormatBitOrder +
        ", bitmapFormatScanlineUnit=" + bitmapFormatScanlineUnit +
        ", bitmapFormatScanlinePad=" + bitmapFormatScanlinePad +
        '}';
  }
}
