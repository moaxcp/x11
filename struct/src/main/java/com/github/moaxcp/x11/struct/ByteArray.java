package com.github.moaxcp.x11.struct;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.moaxcp.x11.struct.ShiftBytes.shiftBytes;
import static com.github.moaxcp.x11.struct.Size.INT8;
import static com.github.moaxcp.x11.struct.Size.INT16;
import static com.github.moaxcp.x11.struct.Size.UINT16;
import static com.github.moaxcp.x11.struct.Size.INT32;
import static com.github.moaxcp.x11.struct.Size.UINT32;
import static com.github.moaxcp.x11.struct.Size.INT64;
import static com.github.moaxcp.x11.struct.Size.UINT64;
import static com.github.moaxcp.x11.struct.Size.FLOAT32;
import static com.github.moaxcp.x11.struct.Size.FLOAT64;
import static com.github.moaxcp.x11.struct.Size.UINT8;

/**
 * A byte array that can grow beyond the max size of a java array.
 */
public class ByteArray {

  private byte[] bytes;
  private final List<ByteArrayListener> listeners = new ArrayList<>();
  private final Serializer serializer;

  // New constructors with default BigEndianSerializer
  public ByteArray() {
    this(new BigEndianSerializer());
  }

  public ByteArray(int size) {
    this(size, new BigEndianSerializer());
  }

  public ByteArray(byte[] bytes) {
    this(bytes, new BigEndianSerializer());
  }

  public ByteArray(Serializer serializer) {
    this.serializer = serializer;
    bytes = new byte[0];
  }

  public ByteArray(int size, Serializer serializer) {
    this.serializer = serializer;
    bytes = new byte[size];
  }

  public ByteArray(byte[] bytes, Serializer serializer) {
    this.serializer = serializer;
    this.bytes = bytes;
  }

  public ByteArray copy() {
    var next = new byte[bytes.length];
    System.arraycopy(bytes, 0, next, 0, next.length);
    return new ByteArray(next, serializer);
  }

  public void addListener(ByteArrayListener listener) {
    listeners.add(listener);
  }

  public void removeListener(ByteArrayListener listener) {
    listeners.remove(listener);
  }

  public void notifyListeners(ShiftBytes shift) {
    listeners.forEach(l -> l.shift(shift));
  }

  byte[] getBytes() {
    return bytes;
  }

  public void setBytes(ByteArray source, long sourceOffset, long index, long length) {
    ensureSizeFor(0, index + length);
    System.arraycopy(source.getBytes(), Math.toIntExact(sourceOffset), bytes, Math.toIntExact(index), Math.toIntExact(length));
  }

  public void ensureSizeFor(long index, long size) {
    if(bytes.length <= index + size) {
      byte[] newBytes = new byte[Math.toIntExact(index + size)];
      System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
      bytes = newBytes;
    }
  }

  public byte int8(long index) {
    return serializer.readInt8(bytes, Math.toIntExact(index));
  }

  public void int8(long index, byte b) {
    serializer.writeInt8(bytes, Math.toIntExact(index), b);
  }

  public void addInt8(long index, byte b) {
    shiftBytesFor(index, INT8.size());
    int8(index, b);
  }

  public void removeInt8(long index) {
    shiftBytesFor(index, -INT8.size());
  }

  public short uint8(long index) {
    return serializer.readUint8(bytes, Math.toIntExact(index));
  }

  public void uint8(long index, short value) {
    serializer.writeUint8(bytes, Math.toIntExact(index), value);
  }

  public void addUint8(long index, short value) {
    shiftBytesFor(index, UINT8.size());
    uint8(index, value);
  }

  public void removeUint8(long index) {
    shiftBytesFor(index, -UINT8.size());
  }

  public short int16(long index) {
    return serializer.readInt16(bytes, Math.toIntExact(index));
  }

  public void int16(long index, short s) {
    serializer.writeInt16(bytes, Math.toIntExact(index), s);
  }

  public void addInt16(long index, short s) {
    shiftBytesFor(index, INT16.size());
    int16(index, s);
  }

  public void removeInt16(long index) {
    shiftBytesFor(index, -INT16.size());
  }

  public int uint16(long index) {
    return serializer.readUint16(bytes, Math.toIntExact(index));
  }

  public void uint16(long index, int value) {
    serializer.writeUint16(bytes, Math.toIntExact(index), value);
  }

  public void addUint16(long index, int value) {
    shiftBytesFor(index, UINT16.size());
    uint16(index, value);
  }

  public void removeUint16(long index) {
    shiftBytesFor(index, -UINT16.size());
  }

  public int int32(long index) {
    return serializer.readInt32(bytes, Math.toIntExact(index));
  }

  public void int32(long index, int value) {
    serializer.writeInt32(bytes, Math.toIntExact(index), value);
  }

  public void addInt32(long index, int value) {
    shiftBytesFor(index, INT32.size());
    int32(index, value);
  }

  public void removeInt32(long index) {
    shiftBytesFor(index, -INT32.size());
  }

  public long uint32(long index) {
    return serializer.readUint32(bytes, Math.toIntExact(index));
  }

  public void uint32(long index, long value) {
    serializer.writeUint32(bytes, Math.toIntExact(index), value);
  }

  public void addUint32(long index, long value) {
    shiftBytesFor(index, UINT32.size());
    uint32(index, value);
  }

  public void removeUint32(long index) {
    shiftBytesFor(index, -UINT32.size());
  }

  public long int64(long index) {
    return serializer.readInt64(bytes, Math.toIntExact(index));
  }

  public void int64(long index, long value) {
    serializer.writeInt64(bytes, Math.toIntExact(index), value);
  }

  public void addInt64(long index, long value) {
    shiftBytesFor(index, INT64.size());
    int64(index, value);
  }

  public void removeInt64(long index) {
    shiftBytesFor(index, -INT64.size());
  }

  public BigInteger uint64(long index) {
    return serializer.readUint64(bytes, Math.toIntExact(index));
  }

  public void uint64(long index, BigInteger value) {
    serializer.writeUint64(bytes, Math.toIntExact(index), value);
  }

  public void addUint64(long index, BigInteger value) {
    shiftBytesFor(index, UINT64.size());
    uint64(index, value);
  }

  public void removeUint64(long index) {
    shiftBytesFor(index, -UINT64.size());
  }

  public float float32(long index) {
    return serializer.readFloat(bytes, Math.toIntExact(index));
  }

  public void float32(long index, float value) {
    serializer.writeFloat(bytes, Math.toIntExact(index), value);
  }

  public void addFloat32(long index, float value) {
    shiftBytesFor(index, FLOAT32.size());
    float32(index, value);
  }

  public void removeFloat32(long index) {
    shiftBytesFor(index, -FLOAT32.size());
  }

  public double float64(long index) {
    return serializer.readDouble(bytes, Math.toIntExact(index));
  }

  public void float64(long index, double value) {
    serializer.writeDouble(bytes, Math.toIntExact(index), value);
  }

  public void addFloat64(long index, double value) {
    shiftBytesFor(index, FLOAT64.size());
    float64(index, value);
  }

  public void removeFloat64(long index) {
    shiftBytesFor(index, -FLOAT64.size());
  }

  public byte[] get(long index, long length) {
    byte[] result = new byte[Math.toIntExact(length)];
    System.arraycopy(bytes, Math.toIntExact(index), result, 0, Math.toIntExact(length));
    return result;
  }

  public void set(long index, byte[] value) {
    System.arraycopy(value, 0, bytes, Math.toIntExact(index), Math.toIntExact(value.length));
  }

  public void add(long index, byte[] value) {
    shiftBytesFor(index, value.length);
    set(index, value);
  }

  public void remove(long index, long byteLength) {
    shiftBytesFor(index, -byteLength);
  }

  private void shiftBytesFor(long index, long size) {
    byte[] newBytes = new byte[Math.toIntExact(bytes.length + size)];
    if (newBytes.length != 0) {
      System.arraycopy(bytes, 0, newBytes, 0, Math.toIntExact(index));
      if (size > 0) {
        System.arraycopy(bytes, Math.toIntExact(index), newBytes, Math.toIntExact(index + size), bytes.length - Math.toIntExact(index));
      } else {
        System.arraycopy(bytes, Math.toIntExact(index - size), newBytes, Math.toIntExact(index), bytes.length - Math.toIntExact(index - size));
      }
    }
    notifyListeners(shiftBytes(index, size));
    bytes = newBytes;
  }

  public boolean compareBytes(long index, ByteArray other, long otherOffset, long length) {
    if(bytes.length < index + length || other.bytes.length < otherOffset + length) {
      return false;
    }
    for(int i = 0; i < length; i++) {
      if(bytes[Math.toIntExact(index + i)] != other.bytes[Math.toIntExact(otherOffset + i)]) {
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
