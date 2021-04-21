package com.github.moaxcp.x11client.protocol;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public interface X11Output {
  void writeBool(boolean bool) throws IOException;

  void writeBool(List<Boolean> bool) throws IOException;

  void writeByte(byte b) throws IOException;

  void writeInt8(byte int8) throws IOException;

  void writeInt16(short int16) throws IOException;

  void writeInt32(int int32) throws IOException;

  void writeInt32(List<Integer> int32) throws IOException;

  void writeInt64(long int64) throws IOException;

  void writeCard8(byte card8) throws IOException;

  void writeCard8(List<Byte> card8) throws IOException;

  void writeCard16(short card16) throws IOException;

  void writeCard16(List<Short> card16) throws IOException;

  void writeCard32(int card32) throws IOException;

  void writeCard32(List<Integer> card32) throws IOException;

  void writeCard64(long card64) throws IOException;

  void writeCard64(List<Long> card64) throws IOException;

  void writeFloat(float f) throws IOException;

  void writeFloat(List<Float> f) throws IOException;

  void writeDouble(double d) throws IOException;

  void writeDouble(List<Double> d) throws IOException;

  void writeChar(List<Byte> string) throws IOException;

  void writeString8(String string) throws IOException;

  void writeByte(List<Byte> bytes) throws IOException;

  void writeVoid(List<Byte> bytes) throws IOException;

  void writeFd(int fd) throws IOException;

  void writeFd(List<Integer> fd) throws IOException;

  default void writePad(int length) throws IOException {
    writeByte(IntStream.range(0, length).mapToObj(i -> (byte) 0).collect(toList()));
  }

  default void writePadAlign(int forLength) throws IOException {
    writePad(XObject.getSizeForPadAlign(forLength));
  }

  default void writePadAlign(int pad, int forLength) throws IOException {
    writePad(XObject.getSizeForPadAlign(pad, forLength));
  }
}
