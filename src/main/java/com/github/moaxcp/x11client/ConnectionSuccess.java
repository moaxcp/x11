package com.github.moaxcp.x11client;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.List;
import lombok.*;

import static com.github.moaxcp.x11client.Format.readFormat;
import static com.github.moaxcp.x11client.Screen.readScreen;

@Value
@Builder
public class ConnectionSuccess {
  int protocolMajorVersion;
  int protocolMinorVersion;
  int releaseNumber;
  int resourceIdBase;
  int resourceIdMask;
  int motionBufferSize;
  int maximumRequestLength;
  ByteOrder imageByteOrder;
  ByteOrder bitmapFormatBitOrder;
  int bitmapFormatScanlineUnit;
  int bitmapFormatScanlinePad;
  int minKeycode;
  int maxKeycode;
  String vendor;
  @Singular
  List<Format> formats;
  @Singular
  List<Screen> screens;

  public static ConnectionSuccess readSuccess(X11Input in) throws IOException {
    in.readByte();
    ConnectionSuccessBuilder builder = ConnectionSuccess.builder();
    builder.protocolMajorVersion(in.readCard16())
        .protocolMinorVersion(in.readCard16());
    int additionalData = in.readCard16();
    builder.releaseNumber(in.readCard32())
        .resourceIdBase(in.readCard32())
        .resourceIdMask(in.readCard32())
        .motionBufferSize(in.readCard32());
    int vendorLength = in.readCard16();
    builder.maximumRequestLength(in.readCard16());
    int numberOfScreens = in.readCard8();
    int numberOfFormats = in.readCard8();
    builder.imageByteOrder(in.readCard8() == 0 ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN)
        .bitmapFormatBitOrder(in.readCard8() == 0 ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN)
        .bitmapFormatScanlineUnit(in.readCard8())
        .bitmapFormatScanlinePad(in.readCard8())
        .minKeycode(in.readCard8())
        .maxKeycode(in.readCard8());
    in.readCard32();
    builder.vendor(in.readString8(vendorLength));
    in.readPad(vendorLength);
    for(int i = 0; i < numberOfFormats; i++) {
      Format format = readFormat(in);
      builder.format(format);
    }
    for(int i = 0; i < numberOfScreens; i++) {
      Screen screen = readScreen(in);
      builder.screen(screen);
    }
    return builder.build();

  }
}
