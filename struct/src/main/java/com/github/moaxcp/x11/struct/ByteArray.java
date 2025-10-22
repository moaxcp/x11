package com.github.moaxcp.x11.struct;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.moaxcp.x11.struct.ShiftBytes.shiftBytes;
import static com.github.moaxcp.x11.struct.Primitive.*;

/**
 * A byte array that can grow beyond the max size of a java array.
 */
public class ByteArray {

  private byte[] bytes;
  private final List<ByteArrayListener> listeners = new ArrayList<>();
  private final Serializer serializer;

  /**
   * Creates a new ByteArray and appends the given values in order using addAll(List).
   * See addAll(long, List) for supported types and dispatching rules.
   */
  public static ByteArray byteArray(List<?> values) {
    ByteArray bytes = new ByteArray();
    bytes.addAll(values);
    return bytes;
  }

  public static ByteArray ba() {
    return new ByteArray();
  }

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

  public ByteArray addListener(ByteArrayListener listener) {
    listeners.add(listener);
    return this;
  }

  public ByteArray removeListener(ByteArrayListener listener) {
    listeners.remove(listener);
    return this;
  }

  public ByteArray notifyListeners(ShiftBytes shift) {
    listeners.forEach(l -> l.shift(shift));
    return this;
  }

  byte[] getBytes() {
    return bytes;
  }

  public ByteArray setBytes(ByteArray source, long sourceOffset, long index, long length) {
    ensureSizeFor(0, index + length);
    System.arraycopy(source.getBytes(), Math.toIntExact(sourceOffset), bytes, Math.toIntExact(index), Math.toIntExact(length));
    return this;
  }

  public ByteArray ensureSizeFor(long index, long size) {
    if(bytes.length <= index + size) {
      byte[] newBytes = new byte[Math.toIntExact(index + size)];
      System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
      bytes = newBytes;
    }
    return this;
  }

  public boolean bool(long index) {
    return serializer.readBoolean(bytes, Math.toIntExact(index));
  }

  public ByteArray bool(long index, boolean value) {
    serializer.writeBoolean(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray addBool(long index, boolean value) {
    shiftBytesFor(index, BOOL.size());
    bool(index, value);
    return this;
  }

  public ByteArray removeBool(long index) {
    shiftBytesFor(index, -BOOL.size());
    return this;
  }

  public byte getInt8(long index) {
    return serializer.readInt8(bytes, Math.toIntExact(index));
  }

  public ByteArray setInt8(long index, byte b) {
    serializer.writeInt8(bytes, Math.toIntExact(index), b);
    return this;
  }

  public ByteArray int8(byte value) {
    return addInt8(bytes.length, value);
  }

  public ByteArray addInt8(long index, byte b) {
    shiftBytesFor(index, INT8.size());
    setInt8(index, b);
    return this;
  }

  public ByteArray removeInt8(long index) {
    shiftBytesFor(index, -INT8.size());
    return this;
  }

  public short getUint8(long index) {
    return serializer.readUint8(bytes, Math.toIntExact(index));
  }

  public ByteArray setUint8(long index, short value) {
    serializer.writeUint8(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray uint8(short value) {
    return addUint8(bytes.length, value);
  }

  public ByteArray addUint8(long index, short value) {
    shiftBytesFor(index, UINT8.size());
    setUint8(index, value);
    return this;
  }

  public ByteArray removeUint8(long index) {
    shiftBytesFor(index, -UINT8.size());
    return this;
  }

  public short getInt16(long index) {
    return serializer.readInt16(bytes, Math.toIntExact(index));
  }

  public ByteArray setInt16(long index, short s) {
    serializer.writeInt16(bytes, Math.toIntExact(index), s);
    return this;
  }

  public ByteArray int16(short value) {
    return addInt16(bytes.length, value);
  }

  public ByteArray addInt16(long index, short s) {
    shiftBytesFor(index, INT16.size());
    setInt16(index, s);
    return this;
  }

  public ByteArray removeInt16(long index) {
    shiftBytesFor(index, -INT16.size());
    return this;
  }

  public int getUint16(long index) {
    return serializer.readUint16(bytes, Math.toIntExact(index));
  }

  public ByteArray setUint16(long index, int value) {
    serializer.writeUint16(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray uint16(int value) {
    return addUint16(bytes.length, value);
  }

  public ByteArray addUint16(long index, int value) {
    shiftBytesFor(index, UINT16.size());
    setUint16(index, value);
    return this;
  }

  public ByteArray removeUint16(long index) {
    shiftBytesFor(index, -UINT16.size());
    return this;
  }

  public int getInt32(long index) {
    return serializer.readInt32(bytes, Math.toIntExact(index));
  }

  public ByteArray setInt32(long index, int value) {
    serializer.writeInt32(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray int32(int value) {
    return addInt32(bytes.length, value);
  }

  public ByteArray addInt32(long index, int value) {
    shiftBytesFor(index, INT32.size());
    setInt32(index, value);
    return this;
  }

  public ByteArray removeInt32(long index) {
    shiftBytesFor(index, -INT32.size());
    return this;
  }

  public long getUint32(long index) {
    return serializer.readUint32(bytes, Math.toIntExact(index));
  }

  public ByteArray setUint32(long index, long value) {
    serializer.writeUint32(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray uint32(Long value) {
    return addUint32(bytes.length, value);
  }

  public ByteArray addUint32(long index, long value) {
    shiftBytesFor(index, UINT32.size());
    setUint32(index, value);
    return this;
  }

  public ByteArray removeUint32(long index) {
    shiftBytesFor(index, -UINT32.size());
    return this;
  }

  public long getInt64(long index) {
    return serializer.readInt64(bytes, Math.toIntExact(index));
  }

  public ByteArray setInt64(long index, long value) {
    serializer.writeInt64(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray int64(Long value) {
    return addInt64(bytes.length, value);
  }

  public ByteArray addInt64(long index, long value) {
    shiftBytesFor(index, INT64.size());
    setInt64(index, value);
    return this;
  }

  public ByteArray removeInt64(long index) {
    shiftBytesFor(index, -INT64.size());
    return this;
  }

  public BigInteger getUint64(long index) {
    return serializer.readUint64(bytes, Math.toIntExact(index));
  }

  public ByteArray setUint64(long index, BigInteger value) {
    serializer.writeUint64(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray uint64(BigInteger value) {
    return addUint64(bytes.length, value);
  }

  public ByteArray addUint64(long index, BigInteger value) {
    shiftBytesFor(index, UINT64.size());
    setUint64(index, value);
    return this;
  }

  public ByteArray removeUint64(long index) {
    shiftBytesFor(index, -UINT64.size());
    return this;
  }

  public float getFloat32(long index) {
    return serializer.readFloat(bytes, Math.toIntExact(index));
  }

  public ByteArray setFloat32(long index, float value) {
    serializer.writeFloat(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray float32(float value) {
    return addFloat32(bytes.length, value);
  }

  public ByteArray addFloat32(long index, float value) {
    shiftBytesFor(index, FLOAT32.size());
    setFloat32(index, value);
    return this;
  }

  public ByteArray removeFloat32(long index) {
    shiftBytesFor(index, -FLOAT32.size());
    return this;
  }

  public double getFloat64(long index) {
    return serializer.readDouble(bytes, Math.toIntExact(index));
  }

  public ByteArray setFloat64(long index, double value) {
    serializer.writeDouble(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray float64(double value) {
    return addFloat64(bytes.length, value);
  }

  public ByteArray addFloat64(long index, double value) {
    shiftBytesFor(index, FLOAT64.size());
    setFloat64(index, value);
    return this;
  }

  public ByteArray removeFloat64(long index) {
    shiftBytesFor(index, -FLOAT64.size());
    return this;
  }

  public Object pad(long length) {
    shiftBytesFor(bytes.length, length);
    return this;
  }

  public byte[] get(long index, long length) {
    byte[] result = new byte[Math.toIntExact(length)];
    System.arraycopy(bytes, Math.toIntExact(index), result, 0, Math.toIntExact(length));
    return result;
  }

  public ByteArray set(long index, byte[] value) {
    System.arraycopy(value, 0, bytes, Math.toIntExact(index), Math.toIntExact(value.length));
    return this;
  }

  public ByteArray add(long index, byte[] value) {
    shiftBytesFor(index, value.length);
    set(index, value);
    return this;
  }

  public ByteArray remove(long index, long byteLength) {
    shiftBytesFor(index, -byteLength);
    return this;
  }

  /**
   * Adds the given values to this byte array at the specified index, in order.
   * Supported Java runtime types and their corresponding serialized types:
   * - Boolean -> bool
   * - Byte -> int8
   * - Short -> int16 (or uint8 if value in range 0..255)
   * - Integer -> int32 (or uint16 if value in range 0..65535)
   * - Long -> int64 (or uint32 if value in range 0..4294967295)
   * - BigInteger -> uint64
   * - Float -> float32
   * - Double -> float64
   * Any other type (including null) will throw an IllegalArgumentException.
   */
  public ByteArray addAll(long index, List<?> values) {
    long currentIndex = index;
    for (Object v : values) {
      if (v == null) {
        throw new IllegalArgumentException("Unsupported value: null");
      }
      if (v instanceof Boolean b) {
        addBool(currentIndex, b);
        currentIndex += BOOL.size();
      } else if (v instanceof Byte b) {
        addInt8(currentIndex, b);
        currentIndex += INT8.size();
      } else if (v instanceof Short s) {
        if (s >= 0 && s <= (short) 0x00FF) {
          addUint8(currentIndex, s);
          currentIndex += UINT8.size();
        } else {
          addInt16(currentIndex, s);
          currentIndex += INT16.size();
        }
      } else if (v instanceof Integer i) {
        if (i >= 0 && i <= 0xFFFF) {
          addUint16(currentIndex, i);
          currentIndex += UINT16.size();
        } else {
          addInt32(currentIndex, i);
          currentIndex += INT32.size();
        }
      } else if (v instanceof Long l) {
        if (l >= 0 && l <= 0xFFFF_FFFFL) {
          addUint32(currentIndex, l);
          currentIndex += UINT32.size();
        } else {
          addInt64(currentIndex, l);
          currentIndex += INT64.size();
        }
      } else if (v instanceof BigInteger bi) {
        addUint64(currentIndex, bi);
        currentIndex += UINT64.size();
      } else if (v instanceof Float f) {
        addFloat32(currentIndex, f);
        currentIndex += FLOAT32.size();
      } else if (v instanceof Double d) {
        addFloat64(currentIndex, d);
        currentIndex += FLOAT64.size();
      } else {
        throw new IllegalArgumentException("Unsupported value type: " + v.getClass().getName());
      }
    }
    return this;
  }

  /**
   * Appends the given values to the end of this byte array, in order.
   * See addAll(long, List) for supported types.
   */
  public ByteArray addAll(List<?> values) {
    addAll(bytes.length, values);
    return this;
  }

  private ByteArray shiftBytesFor(long index, long size) {
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
    return this;
  }

  public ByteArray replace(long index, long length, ByteArray source, long sourceIndex, long sourceLength) {
    byte[] newBytes = new byte[Math.toIntExact(bytes.length - length + sourceLength)];
    System.arraycopy(bytes, 0, newBytes, 0, Math.toIntExact(index));
    System.arraycopy(source.getBytes(), Math.toIntExact(sourceIndex), newBytes, Math.toIntExact(index), Math.toIntExact(sourceLength));
    System.arraycopy(bytes, Math.toIntExact(index + length), newBytes, Math.toIntExact(index + sourceLength), bytes.length - Math.toIntExact(index + length));
    bytes = newBytes;
    return this;
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

  @Override
  public String toString() {
    return Arrays.toString(bytes);
  }
}
