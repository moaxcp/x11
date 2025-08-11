package com.github.moaxcp.x11.struct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ByteArray {

  public record ByteShift(int offset, int size) { }

  @FunctionalInterface
  public interface Listener {
    void event(ByteShift shift);
  }

  public static ByteShift shift(int offset, int size) {
    return new ByteShift(offset, size);
  }

  private byte[] bytes;
  private final List<Listener> listeners = new ArrayList<>();

  public ByteArray() {
    bytes = new byte[0];
  }

  public ByteArray(int size) {
    bytes = new byte[size];
  }

  public ByteArray(byte[] bytes) {
    this.bytes = bytes;
  }

  public ByteArray copy() {
    var next = new byte[bytes.length];
    System.arraycopy(bytes, 0, next, 0, next.length);
    return new ByteArray(next);
  }

  public void addListener(Listener listener) {
    listeners.add(listener);
  }

  public void removeListener(Listener listener) {
    listeners.remove(listener);
  }

  public void notifyListeners(ByteShift shift) {
    listeners.forEach(l -> l.event(shift));
  }

  public byte[] getBytes() {
    return bytes;
  }

  public void setBytes(ByteArray source, int sourceOffset, int offset, int length) {
    ensureSizeFor(0, offset + length);
    System.arraycopy(source.getBytes(), sourceOffset, bytes, offset, length);
  }

  public void ensureSizeFor(int offset, int size) {
    if(bytes.length <= offset + size) {
      byte[] newBytes = new byte[offset + size];
      System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
      bytes = newBytes;
    }
  }

  public byte getByte(int offset) {
    return bytes[offset];
  }

  public ByteArray setByte(int offset, byte b) {
    ensureSizeFor(offset, ByteType.SIZE);
    bytes[offset] = b;
    return this;
  }

  public ByteArray addByte(int offset, byte b) {
    shiftBytesFor(offset, ByteType.SIZE);
    setByte(offset, b);
    notifyListeners(new ByteShift(offset, ByteType.SIZE));
    return this;
  }

  private void shiftBytesFor(int offset, int size) {
    byte[] newBytes = new byte[bytes.length + size];
    System.arraycopy(bytes, 0, newBytes, 0, offset);
    System.arraycopy(bytes, offset, newBytes, offset + size, bytes.length - offset);
    bytes = newBytes;
  }

  public short getShort(int offset) {
    return (short) (((bytes[offset] & 0xFF) << 8) | (bytes[offset + 1] & 0xFF));
  }

  public ByteArray setShort(int offset, short s) {
    ensureSizeFor(offset, ShortType.SIZE);
    bytes[offset] = (byte) (s >> 8);
    bytes[offset + 1] = (byte) s;
    return this;
  }

  public ByteArray addShort(int offset, short s) {
    shiftBytesFor(offset, ShortType.SIZE);
    setShort(offset, s);
    notifyListeners(new ByteShift(offset, ShortType.SIZE));
    return this;
  }

  public boolean compareBytes(int offset, ByteArray other, int otherOffset, int length) {
    if(bytes.length < offset + length || other.bytes.length < otherOffset + length) {
      return false;
    }
    for(int i = 0; i < length; i++) {
      if(bytes[offset + i] != other.bytes[otherOffset + i]) {
        return false;
      }
    }
    return true;
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof ByteArray byteArray)) return false;

    return Arrays.equals(bytes, byteArray.bytes);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(bytes);
  }
}
