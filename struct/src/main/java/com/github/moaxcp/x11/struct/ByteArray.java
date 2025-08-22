package com.github.moaxcp.x11.struct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.moaxcp.x11.struct.NumberType.Size.BYTE;
import static com.github.moaxcp.x11.struct.NumberType.Size.SHORT;

public class ByteArray {

  public record ShiftBytes(long offset, long size) { }

  public interface Listener {
    void shift(ShiftBytes shift);
  }

  public static ShiftBytes shiftBytes(long offset, long size) {
    return new ShiftBytes(offset, size);
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

  public void notifyListeners(ShiftBytes shift) {
    listeners.forEach(l -> l.shift(shift));
  }

  byte[] getBytes() {
    return bytes;
  }

  public void setBytes(ByteArray source, long sourceOffset, long offset, long length) {
    ensureSizeFor(0, offset + length);
    System.arraycopy(source.getBytes(), Math.toIntExact(sourceOffset), bytes, Math.toIntExact(offset), Math.toIntExact(length));
  }

  public void ensureSizeFor(long offset, long size) {
    if(bytes.length <= offset + size) {
      byte[] newBytes = new byte[Math.toIntExact(offset + size)];
      System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
      bytes = newBytes;
    }
  }

  public long getByte(long offset) {
    return bytes[Math.toIntExact(offset)];
  }

  public void setByte(long offset, long b) {
    ensureSizeFor(offset, BYTE.size());
    bytes[Math.toIntExact(offset)] = (byte) b;
  }

  public void addByte(long offset, long b) {
    shiftBytesFor(offset, BYTE.size());
    setByte(offset, b);
  }

  public void removeByte(long offset) {
    shiftBytesFor(offset, -BYTE.size());
  }

  public void remove(long offset, long byteLength) {
    shiftBytesFor(offset, -byteLength);
  }

  private void shiftBytesFor(long offset, long size) {
    byte[] newBytes = new byte[Math.toIntExact(bytes.length + size)];
    if (newBytes.length != 0) {
      System.arraycopy(bytes, 0, newBytes, 0, Math.toIntExact(offset));
      if (size > 0) {
        System.arraycopy(bytes, Math.toIntExact(offset), newBytes, Math.toIntExact(offset + size), bytes.length - Math.toIntExact(offset));
      } else {
        System.arraycopy(bytes, Math.toIntExact(offset - size), newBytes, Math.toIntExact(offset), bytes.length - Math.toIntExact(offset - size));
      }
    }
    notifyListeners(shiftBytes(offset, size));
    bytes = newBytes;
  }

  public long getShort(long offset) {
    return (short) (((bytes[Math.toIntExact(offset)] & 0xFF) << 8) | (bytes[Math.toIntExact(offset + 1)] & 0xFF));
  }

  public ByteArray setShort(long offset, long s) {
    ensureSizeFor(offset, SHORT.size());
    bytes[Math.toIntExact(offset)] = (byte) (s >> 8);
    bytes[Math.toIntExact(offset + 1)] = (byte) s;
    return this;
  }

  public ByteArray addShort(long offset, long s) {
    shiftBytesFor(offset, SHORT.size());
    setShort(offset, s);
    return this;
  }

  public ByteArray removeShort(long offset) {
    shiftBytesFor(offset, -SHORT.size());
    return this;
  }

  public boolean compareBytes(long offset, ByteArray other, long otherOffset, long length) {
    if(bytes.length < offset + length || other.bytes.length < otherOffset + length) {
      return false;
    }
    for(int i = 0; i < length; i++) {
      if(bytes[Math.toIntExact(offset + i)] != other.bytes[Math.toIntExact(otherOffset + i)]) {
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
