package com.github.moaxcp.x11.struct;

import java.math.BigInteger;
import java.util.*;

import static com.github.moaxcp.x11.struct.BigEndianSerializer.bigEndianSerializer;
import static com.github.moaxcp.x11.struct.Primitive.*;
import static com.github.moaxcp.x11.struct.ShiftBytes.shiftBytes;

/**
 * A byte array that can grow beyond the max size of a java array.
 */
public class ByteArray {

  private byte[] bytes;
  private int allocated;
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

  public static ByteArray ba(int size) {
    return new ByteArray(size);
  }

  public static ByteArray ba(byte[] bytes) {
    return new ByteArray(bytes);
  }

  // New constructors with default BigEndianSerializer
  public ByteArray() {
    this(bigEndianSerializer());
  }

  public ByteArray(int size) {
    this(size, bigEndianSerializer());
  }

  public ByteArray(byte[] bytes) {
    this(bytes, bigEndianSerializer());
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
    this.allocated = bytes.length;
  }

  public ByteArray copy() {
    var next = new byte[bytes.length];
    System.arraycopy(bytes, 0, next, 0, next.length);
    return new ByteArray(next, serializer);
  }

  public List<ByteArrayListener> getListeners() {
    return Collections.unmodifiableList(listeners);
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

  int getAllocated() {
    return allocated;
  }

  public ByteArray setBytes(ByteArray source, long sourceOffset, long index, long length) {
    ensureSizeFor(0, index + length);
    System.arraycopy(source.bytes, Math.toIntExact(sourceOffset), bytes, Math.toIntExact(index), Math.toIntExact(length));
    if (allocated < index + length) {
      allocated = Math.toIntExact(index + length);
    }
    return this;
  }

  ByteArray ensureSizeFor(long index, long size) {
    if(bytes.length <= index + size) {
      byte[] newBytes = new byte[Math.toIntExact(index + size)];
      System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
      bytes = newBytes;
    }
    return this;
  }

  public ByteArray allocate(long size) {
    ensureSizeFor(allocated, size);
    allocated += size;
    return this;
  }

  private void checkAllocation(long index, long length) {
    if (allocated < index + length) {
      throw new IndexOutOfBoundsException("cannot allocate more bytes allocated: " + allocated + ", index: " + index + ", length: " + length);
    }
  }

  public boolean getBool(long index) {
    return serializer.readBool(bytes, Math.toIntExact(index));
  }

  public boolean[] getBool(long index, long length) {
    return serializer.readBool(bytes, Math.toIntExact(index), Math.toIntExact(length));
  }

  public ByteArray setBool(long index, boolean value) {
    checkAllocation(index, BOOL.size());
    serializer.writeBool(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray setBool(long index, boolean... values) {
    checkAllocation(index, BOOL.size() * values.length);
    serializer.writeBool(bytes, Math.toIntExact(index), values);
    return this;
  }

  public ByteArray setBool(long index, List<Boolean> values) {
    checkAllocation(index, BOOL.size() * values.size());
    for (int i = 0; i < values.size(); i++) {
      serializer.writeBool(bytes, Math.toIntExact(index + i), values.get(i));
    }
    return this;
  }


  public ByteArray bool(boolean value) {
    return addBool(allocated, value);
  }

  public ByteArray bool(boolean... values) {
    return addBool(allocated, values);
  }

  public ByteArray bool(List<Boolean> values) {
    return addBool(allocated, values);
  }

  public ByteArray addBool(long index, boolean value) {
    shiftBytesFor(index, BOOL.size());
    setBool(index, value);
    return this;
  }

  public ByteArray addBool(long index, boolean... values) {
    if(values == null || values.length == 0) {
      return this;
    }
    shiftBytesFor(index, BOOL.size() * values.length);
    setBool(index, values);
    return this;
  }

  public ByteArray addBool(long index, List<Boolean> values) {
    if (values == null || values.isEmpty()) {
      return this;
    }
    shiftBytesFor(index, BOOL.size() * values.size());
    setBool(index, values);
    return this;
  }


  public ByteArray removeBool(long index) {
    shiftBytesFor(index, -BOOL.size());
    return this;
  }

  public ByteArray removeBool(long index, long length) {
    shiftBytesFor(index, -BOOL.size() * length);
    return this;
  }

  public byte getInt8(long index) {
    return serializer.readInt8(bytes, Math.toIntExact(index));
  }

  public byte[] getInt8(long index, long length) {
    return serializer.readInt8(bytes, Math.toIntExact(index), Math.toIntExact(length));
  }

  public ByteArray setInt8(long index, byte b) {
    checkAllocation(index, INT8.size());
    serializer.writeInt8(bytes, Math.toIntExact(index), b);
    return this;
  }

  public ByteArray setInt8(long index, int b) {
    return setInt8(index, (byte) b);
  }

  public ByteArray setInt8(long index, byte... values) {
    checkAllocation(index, INT8.size() * values.length);
    serializer.writeInt8(bytes, Math.toIntExact(index), values);
    return this;
  }

  public ByteArray setInt8(long index, int... values) {
    var b = new byte[values.length];
    for(int i = 0; i < values.length; i++) {
      b[i] = (byte) values[i];
    }
    return setInt8(index, b);
  }

  public ByteArray int8(byte value) {
    return addInt8(allocated, value);
  }

  public ByteArray int8(int value) {
    return int8((byte) value);
  }

  public ByteArray int8(byte... values) {
    return addInt8(allocated, values);
  }

  public ByteArray int8(int... values) {
    return addInt8(allocated, values);
  }

  public ByteArray addInt8(long index, byte b) {
    shiftBytesFor(index, INT8.size());
    setInt8(index, b);
    return this;
  }

  public ByteArray addInt8(long index, int b) {
    addInt8(index, (byte) b);
    return this;
  }

  public ByteArray addInt8(long index, byte... values) {
    if(values == null || values.length == 0) {
      return this;
    }
    shiftBytesFor(index, INT8.size() * values.length);
    setInt8(index, values);
    return this;
  }

  public ByteArray addInt8(long index, int... values) {
    var b = new byte[values.length];
    for(int i = 0; i < values.length; i++) {
      b[i] = (byte) values[i];
    }
    return addInt8(index, b);
  }

  public ByteArray removeInt8(long index) {
    shiftBytesFor(index, -INT8.size());
    return this;
  }

  public ByteArray removeInt8(long index, long length) {
    shiftBytesFor(index, -INT8.size() * length);
    return this;
  }

  public short getUint8(long index) {
    return serializer.readUint8(bytes, Math.toIntExact(index));
  }

  public short[] getUint8(long index, long length) {
    return serializer.readUint8(bytes, Math.toIntExact(index), Math.toIntExact(length));
  }

  public ByteArray setUint8(long index, short value) {
    serializer.writeUint8(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray setUint8(long index, short[] values) {
    serializer.writeUint8(bytes, Math.toIntExact(index), values);
    return this;
  }

  public ByteArray uint8(short value) {
    return addUint8(bytes.length, value);
  }

  public ByteArray uint8(short... values) {
    return addUint8(bytes.length, values);
  }

  public ByteArray uint8(int... values) {
    var s = new short[values.length];
    for(int i = 0; i < values.length; i++) {
      s[i] = (short) values[i];
    }
    return addUint8(bytes.length, s);
  }

  public ByteArray addUint8(long index, short value) {
    shiftBytesFor(index, UINT8.size());
    setUint8(index, value);
    return this;
  }

  public ByteArray addUint8(long index, short[] values) {
    if(values == null || values.length == 0) {
      return this;
    }
    shiftBytesFor(index, UINT8.size() * values.length);
    setUint8(index, values);
    return this;
  }

  public ByteArray removeUint8(long index) {
    shiftBytesFor(index, -UINT8.size());
    return this;
  }

  public ByteArray removeUint8(long index, long length) {
    shiftBytesFor(index, -UINT8.size() * length);
    return this;
  }

  public short getInt16(long index) {
    return serializer.readInt16(bytes, Math.toIntExact(index));
  }

  public short[] getInt16(long index, long length) {
    return serializer.readInt16(bytes, Math.toIntExact(index), Math.toIntExact(length));
  }

  public ByteArray setInt16(long index, short s) {
    serializer.writeInt16(bytes, Math.toIntExact(index), s);
    return this;
  }

  public ByteArray setInt16(long index, short[] values) {
    serializer.writeInt16(bytes, Math.toIntExact(index), values);
    return this;
  }

  public ByteArray int16(short value) {
    return addInt16(bytes.length, value);
  }

  public ByteArray int16(short... values) {
    return addInt16(bytes.length, values);
  }

  public ByteArray int16(int... values) {
    var s = new short[values.length];
    for(int i = 0; i < values.length; i++) {
      s[i] = (short) values[i];
    }
    return addInt16(bytes.length, s);
  }

  public ByteArray addInt16(long index, short s) {
    shiftBytesFor(index, INT16.size());
    setInt16(index, s);
    return this;
  }

  public ByteArray addInt16(long index, short[] values) {
    if(values == null || values.length == 0) {
      return this;
    }
    shiftBytesFor(index, INT16.size() * values.length);
    setInt16(index, values);
    return this;
  }

  public ByteArray removeInt16(long index) {
    shiftBytesFor(index, -INT16.size());
    return this;
  }

  public ByteArray removeInt16(long index, long length) {
    shiftBytesFor(index, -INT16.size() * length);
    return this;
  }

  public int getUint16(long index) {
    return serializer.readUint16(bytes, Math.toIntExact(index));
  }

  public int[] getUint16(long index, long length) {
    return serializer.readUint16(bytes, Math.toIntExact(index), Math.toIntExact(length));
  }

  public ByteArray setUint16(long index, int value) {
    serializer.writeUint16(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray setUint16(long index, int[] values) {
    serializer.writeUint16(bytes, Math.toIntExact(index), values);
    return this;
  }

  public ByteArray uint16(int value) {
    return addUint16(bytes.length, value);
  }

  public ByteArray uint16(int... values) {
    return addUint16(bytes.length, values);
  }

  public ByteArray addUint16(long index, int value) {
    shiftBytesFor(index, UINT16.size());
    setUint16(index, value);
    return this;
  }

  public ByteArray addUint16(long index, int[] values) {
    if(values == null || values.length == 0) {
      return this;
    }
    shiftBytesFor(index, UINT16.size() * values.length);
    setUint16(index, values);
    return this;
  }

  public ByteArray removeUint16(long index) {
    shiftBytesFor(index, -UINT16.size());
    return this;
  }

  public ByteArray removeUint16(long index, long length) {
    shiftBytesFor(index, -UINT16.size() * length);
    return this;
  }

  public int getInt32(long index) {
    return serializer.readInt32(bytes, Math.toIntExact(index));
  }

  public int[] getInt32(long index, long length) {
    return serializer.readInt32(bytes, Math.toIntExact(index), Math.toIntExact(length));
  }

  public ByteArray setInt32(long index, int value) {
    serializer.writeInt32(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray setInt32(long index, int[] values) {
    serializer.writeInt32(bytes, Math.toIntExact(index), values);
    return this;
  }

  public ByteArray int32(int value) {
    return addInt32(bytes.length, value);
  }

  public ByteArray int32(int... values) {
    return addInt32(bytes.length, values);
  }

  public ByteArray addInt32(long index, int value) {
    shiftBytesFor(index, INT32.size());
    setInt32(index, value);
    return this;
  }

  public ByteArray addInt32(long index, int[] values) {
    if(values == null || values.length == 0) {
      return this;
    }
    shiftBytesFor(index, INT32.size() * values.length);
    setInt32(index, values);
    return this;
  }

  public ByteArray removeInt32(long index) {
    shiftBytesFor(index, -INT32.size());
    return this;
  }

  public ByteArray removeInt32(long index, long length) {
    shiftBytesFor(index, -INT32.size() * length);
    return this;
  }

  public long getUint32(long index) {
    return serializer.readUint32(bytes, Math.toIntExact(index));
  }

  public long[] getUint32(long index, long length) {
    return serializer.readUint32(bytes, Math.toIntExact(index), Math.toIntExact(length));
  }

  public ByteArray setUint32(long index, long value) {
    serializer.writeUint32(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray setUint32(long index, long[] values) {
    serializer.writeUint32(bytes, Math.toIntExact(index), values);
    return this;
  }

  public ByteArray uint32(Long value) {
    return addUint32(bytes.length, value);
  }

  public ByteArray uint32(long... values) {
    return addUint32(bytes.length, values);
  }

  public ByteArray addUint32(long index, long value) {
    shiftBytesFor(index, UINT32.size());
    setUint32(index, value);
    return this;
  }

  public ByteArray addUint32(long index, long[] values) {
    if(values == null || values.length == 0) {
      return this;
    }
    shiftBytesFor(index, UINT32.size() * values.length);
    setUint32(index, values);
    return this;
  }

  public ByteArray removeUint32(long index) {
    shiftBytesFor(index, -UINT32.size());
    return this;
  }

  public ByteArray removeUint32(long index, long length) {
    shiftBytesFor(index, -UINT32.size() * length);
    return this;
  }

  public long getInt64(long index) {
    return serializer.readInt64(bytes, Math.toIntExact(index));
  }

  public long[] getInt64(long index, long length) {
    return serializer.readInt64(bytes, Math.toIntExact(index), Math.toIntExact(length));
  }

  public ByteArray setInt64(long index, long value) {
    serializer.writeInt64(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray setInt64(long index, long[] values) {
    serializer.writeInt64(bytes, Math.toIntExact(index), values);
    return this;
  }

  public ByteArray int64(Long value) {
    return addInt64(bytes.length, value);
  }

  public ByteArray int64(long... values) {
    return addInt64(bytes.length, values);
  }

  public ByteArray addInt64(long index, long value) {
    shiftBytesFor(index, INT64.size());
    setInt64(index, value);
    return this;
  }

  public ByteArray addInt64(long index, long[] values) {
    if(values == null || values.length == 0) {
      return this;
    }
    shiftBytesFor(index, INT64.size() * values.length);
    setInt64(index, values);
    return this;
  }

  public ByteArray removeInt64(long index) {
    shiftBytesFor(index, -INT64.size());
    return this;
  }

  public ByteArray removeInt64(long index, long length) {
    shiftBytesFor(index, -INT64.size() * length);
    return this;
  }

  public BigInteger getUint64(long index) {
    return serializer.readUint64(bytes, Math.toIntExact(index));
  }

  public BigInteger[] getUint64(long index, long length) {
    return serializer.readUint64(bytes, Math.toIntExact(index), Math.toIntExact(length));
  }

  public ByteArray setUint64(long index, BigInteger value) {
    serializer.writeUint64(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray setUint64(long index, BigInteger[] values) {
    serializer.writeUint64(bytes, Math.toIntExact(index), values);
    return this;
  }

  public ByteArray uint64(BigInteger value) {
    return addUint64(bytes.length, value);
  }

  public ByteArray uint64(BigInteger... values) {
    return addUint64(bytes.length, values);
  }

  public ByteArray addUint64(long index, BigInteger value) {
    shiftBytesFor(index, UINT64.size());
    setUint64(index, value);
    return this;
  }

  public ByteArray addUint64(long index, BigInteger[] values) {
    if(values == null || values.length == 0) {
      return this;
    }
    shiftBytesFor(index, UINT64.size() * values.length);
    setUint64(index, values);
    return this;
  }

  public ByteArray removeUint64(long index) {
    shiftBytesFor(index, -UINT64.size());
    return this;
  }

  public ByteArray removeUint64(long index, long length) {
    shiftBytesFor(index, -UINT64.size() * length);
    return this;
  }

  public float getFloat32(long index) {
    return serializer.readFloat32(bytes, Math.toIntExact(index));
  }

  public float[] getFloat32(long index, long length) {
    return serializer.readFloat32(bytes, Math.toIntExact(index), Math.toIntExact(length));
  }

  public ByteArray setFloat32(long index, float value) {
    serializer.writeFloat32(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray setFloat32(long index, float[] values) {
    serializer.writeFloat32(bytes, Math.toIntExact(index), values);
    return this;
  }

  public ByteArray float32(float value) {
    return addFloat32(bytes.length, value);
  }

  public ByteArray float32(float... values) {
    return addFloat32(bytes.length, values);
  }

  public ByteArray addFloat32(long index, float value) {
    shiftBytesFor(index, FLOAT32.size());
    setFloat32(index, value);
    return this;
  }

  public ByteArray addFloat32(long index, float[] values) {
    if(values == null || values.length == 0) {
      return this;
    }
    shiftBytesFor(index, FLOAT32.size() * values.length);
    setFloat32(index, values);
    return this;
  }

  public ByteArray removeFloat32(long index) {
    shiftBytesFor(index, -FLOAT32.size());
    return this;
  }

  public ByteArray removeFloat32(long index, long length) {
    shiftBytesFor(index, -FLOAT32.size() * length);
    return this;
  }

  public double getFloat64(long index) {
    return serializer.readFloat64(bytes, Math.toIntExact(index));
  }

  public double[] getFloat64(long index, long length) {
    return serializer.readFloat64(bytes, Math.toIntExact(index), Math.toIntExact(length));
  }

  public ByteArray setFloat64(long index, double value) {
    serializer.writeFloat64(bytes, Math.toIntExact(index), value);
    return this;
  }

  public ByteArray setFloat64(long index, double[] values) {
    serializer.writeFloat64(bytes, Math.toIntExact(index), values);
    return this;
  }

  public ByteArray float64(double value) {
    return addFloat64(bytes.length, value);
  }

  public ByteArray float64(double... values) {
    return addFloat64(bytes.length, values);
  }

  public ByteArray addFloat64(long index, double value) {
    shiftBytesFor(index, FLOAT64.size());
    setFloat64(index, value);
    return this;
  }

  public ByteArray addFloat64(long index, double[] values) {
    if(values == null || values.length == 0) {
      return this;
    }
    shiftBytesFor(index, FLOAT64.size() * values.length);
    setFloat64(index, values);
    return this;
  }

  public ByteArray removeFloat64(long index) {
    shiftBytesFor(index, -FLOAT64.size());
    return this;
  }

  public ByteArray removeFloat64(long index, long length) {
    shiftBytesFor(index, -FLOAT64.size() * length);
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
    addAll(allocated, values);
    return this;
  }

  private ByteArray shiftBytesFor(long index, long size) {
    if (bytes.length >= allocated + size) {
      if (size > 0) {
        System.arraycopy(bytes, Math.toIntExact(index), bytes, Math.toIntExact(index + size), Math.toIntExact(allocated - index));
      } else {
        System.arraycopy(bytes, Math.toIntExact(index - size), bytes, Math.toIntExact(index), Math.toIntExact(allocated - (index - size)));
      }
    } else {
      byte[] newBytes = new byte[Math.toIntExact(allocated + size)];
      if (newBytes.length != 0) {
        System.arraycopy(bytes, 0, newBytes, 0, Math.toIntExact(index));
        if (size > 0) {
          System.arraycopy(bytes, Math.toIntExact(index), newBytes, Math.toIntExact(index + size), allocated - Math.toIntExact(index));
        } else {
          System.arraycopy(bytes, Math.toIntExact(index - size), newBytes, Math.toIntExact(index), allocated - Math.toIntExact(index - size));
        }
      }
      bytes = newBytes;
    }
    allocated += Math.toIntExact(size);
    notifyListeners(shiftBytes(index, size));
    return this;
  }

  public ByteArray replace(long index, long length, ByteArray source, long sourceIndex, long sourceLength) {
    byte[] newBytes = new byte[Math.toIntExact(bytes.length - length + sourceLength)];
    System.arraycopy(bytes, 0, newBytes, 0, Math.toIntExact(index));
    System.arraycopy(source.bytes, Math.toIntExact(sourceIndex), newBytes, Math.toIntExact(index), Math.toIntExact(sourceLength));
    System.arraycopy(bytes, Math.toIntExact(index + length), newBytes, Math.toIntExact(index + sourceLength), bytes.length - Math.toIntExact(index + length));
    bytes = newBytes;
    return this;
  }

  public boolean compareBytes(long index, ByteArray other, long otherOffset, long length) {
    if(allocated < index + length || other.allocated < otherOffset + length) {
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

    return allocated == byteArray.allocated && compareBytes(0, byteArray, 0, allocated) && Objects.equals(serializer, byteArray.serializer);
  }

  @Override
  public int hashCode() {
    int result = Arrays.hashCode(bytes);
    result = 31 * result + allocated;
    return result;
  }

  @Override
  public String toString() {
    return "ByteArray{" +
        "bytes=" + Arrays.toString(bytes) +
        ", allocated=" + allocated +
        ", serializer=" + serializer +
        ", listeners=" + listeners.size() +
        '}';
  }
}
