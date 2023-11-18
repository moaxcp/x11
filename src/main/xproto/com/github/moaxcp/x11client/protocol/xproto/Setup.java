package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Setup implements XStruct, XprotoObject {
  private byte status;

  private short protocolMajorVersion;

  private short protocolMinorVersion;

  private short length;

  private int releaseNumber;

  private int resourceIdBase;

  private int resourceIdMask;

  private int motionBufferSize;

  private short maximumRequestLength;

  private byte imageByteOrder;

  private byte bitmapFormatBitOrder;

  private byte bitmapFormatScanlineUnit;

  private byte bitmapFormatScanlinePad;

  private byte minKeycode;

  private byte maxKeycode;

  @NonNull
  private List<Byte> vendor;

  @NonNull
  private List<Format> pixmapFormats;

  @NonNull
  private List<Screen> roots;

  public static Setup readSetup(X11Input in) throws IOException {
    Setup.SetupBuilder javaBuilder = Setup.builder();
    byte status = in.readCard8();
    byte[] pad1 = in.readPad(1);
    short protocolMajorVersion = in.readCard16();
    short protocolMinorVersion = in.readCard16();
    short length = in.readCard16();
    int releaseNumber = in.readCard32();
    int resourceIdBase = in.readCard32();
    int resourceIdMask = in.readCard32();
    int motionBufferSize = in.readCard32();
    short vendorLen = in.readCard16();
    short maximumRequestLength = in.readCard16();
    byte rootsLen = in.readCard8();
    byte pixmapFormatsLen = in.readCard8();
    byte imageByteOrder = in.readCard8();
    byte bitmapFormatBitOrder = in.readCard8();
    byte bitmapFormatScanlineUnit = in.readCard8();
    byte bitmapFormatScanlinePad = in.readCard8();
    byte minKeycode = in.readCard8();
    byte maxKeycode = in.readCard8();
    byte[] pad19 = in.readPad(4);
    List<Byte> vendor = in.readChar(Short.toUnsignedInt(vendorLen));
    in.readPadAlign(Short.toUnsignedInt(vendorLen));
    List<Format> pixmapFormats = new ArrayList<>(Byte.toUnsignedInt(pixmapFormatsLen));
    for(int i = 0; i < Byte.toUnsignedInt(pixmapFormatsLen); i++) {
      pixmapFormats.add(Format.readFormat(in));
    }
    List<Screen> roots = new ArrayList<>(Byte.toUnsignedInt(rootsLen));
    for(int i = 0; i < Byte.toUnsignedInt(rootsLen); i++) {
      roots.add(Screen.readScreen(in));
    }
    javaBuilder.status(status);
    javaBuilder.protocolMajorVersion(protocolMajorVersion);
    javaBuilder.protocolMinorVersion(protocolMinorVersion);
    javaBuilder.length(length);
    javaBuilder.releaseNumber(releaseNumber);
    javaBuilder.resourceIdBase(resourceIdBase);
    javaBuilder.resourceIdMask(resourceIdMask);
    javaBuilder.motionBufferSize(motionBufferSize);
    javaBuilder.maximumRequestLength(maximumRequestLength);
    javaBuilder.imageByteOrder(imageByteOrder);
    javaBuilder.bitmapFormatBitOrder(bitmapFormatBitOrder);
    javaBuilder.bitmapFormatScanlineUnit(bitmapFormatScanlineUnit);
    javaBuilder.bitmapFormatScanlinePad(bitmapFormatScanlinePad);
    javaBuilder.minKeycode(minKeycode);
    javaBuilder.maxKeycode(maxKeycode);
    javaBuilder.vendor(vendor);
    javaBuilder.pixmapFormats(pixmapFormats);
    javaBuilder.roots(roots);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(status);
    out.writePad(1);
    out.writeCard16(protocolMajorVersion);
    out.writeCard16(protocolMinorVersion);
    out.writeCard16(length);
    out.writeCard32(releaseNumber);
    out.writeCard32(resourceIdBase);
    out.writeCard32(resourceIdMask);
    out.writeCard32(motionBufferSize);
    short vendorLen = (short) vendor.size();
    out.writeCard16(vendorLen);
    out.writeCard16(maximumRequestLength);
    byte rootsLen = (byte) roots.size();
    out.writeCard8(rootsLen);
    byte pixmapFormatsLen = (byte) pixmapFormats.size();
    out.writeCard8(pixmapFormatsLen);
    out.writeCard8(imageByteOrder);
    out.writeCard8(bitmapFormatBitOrder);
    out.writeCard8(bitmapFormatScanlineUnit);
    out.writeCard8(bitmapFormatScanlinePad);
    out.writeCard8(minKeycode);
    out.writeCard8(maxKeycode);
    out.writePad(4);
    out.writeChar(vendor);
    out.writePadAlign(Short.toUnsignedInt(vendorLen));
    for(Format t : pixmapFormats) {
      t.write(out);
    }
    for(Screen t : roots) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 40 + 1 * vendor.size() + XObject.getSizeForPadAlign(4, 1 * vendor.size()) + XObject.sizeOf(pixmapFormats) + XObject.sizeOf(roots);
  }

  public static class SetupBuilder {
    public Setup.SetupBuilder imageByteOrder(ImageOrder imageByteOrder) {
      this.imageByteOrder = (byte) imageByteOrder.getValue();
      return this;
    }

    public Setup.SetupBuilder imageByteOrder(byte imageByteOrder) {
      this.imageByteOrder = imageByteOrder;
      return this;
    }

    public Setup.SetupBuilder bitmapFormatBitOrder(ImageOrder bitmapFormatBitOrder) {
      this.bitmapFormatBitOrder = (byte) bitmapFormatBitOrder.getValue();
      return this;
    }

    public Setup.SetupBuilder bitmapFormatBitOrder(byte bitmapFormatBitOrder) {
      this.bitmapFormatBitOrder = bitmapFormatBitOrder;
      return this;
    }

    public int getSize() {
      return 40 + 1 * vendor.size() + XObject.getSizeForPadAlign(4, 1 * vendor.size()) + XObject.sizeOf(pixmapFormats) + XObject.sizeOf(roots);
    }
  }
}
