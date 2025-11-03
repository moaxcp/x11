package com.github.moaxcp.x11.struct;

import java.math.BigInteger;
import java.util.function.Consumer;

public class Struct implements Pointer<Struct, StructType> {
  private boolean allocated = false;
  private long offset;
  private StructType structType;
  private ByteArray bytes;
  private ByteArrayListener listener;

   public Struct(Struct struct) {
    this(struct.allocated, struct.offset, struct.structType.copy(-1), struct.bytes.copy());
   }

  public Struct(StructType structType) {
    this(0, structType);
  }

  public Struct(StructType structType, ByteArray bytes) {
    this(true, 0, structType, bytes);
  }

  public Struct(int offset, StructType structType) {
    this(false, offset, structType, new ByteArray());
  }

  public Struct(long offset, StructType structType, ByteArray bytes) {
    this(true, offset, structType, bytes);
  }

  public Struct(boolean allocated, long offset, StructType structType, ByteArray bytes) {
     this.allocated = allocated;
    this.offset = offset;
    this.structType = structType;
    this.bytes = bytes;
    if (!this.allocated) {
      structType.allocate(this);
    }
    listener = shift -> {
      var o = getOffset();
      if (o >= shift.offset()) {
        setOffset(o + shift.size());
      }
    };
    bytes.addListener(listener);
  }

  @Override
  public Struct copy() {
    return new Struct(this);
  }

  @Override
  public long getOffset() {
    return offset;
  }

  @Override
  public Struct setOffset(long offset) {
    this.offset = offset;
    return this;
  }

  @Override
  public StructType getType() {
    return structType;
  }

  @Override
  public <V extends Type<?>> V getType(int position) {
    return structType.getType(position);
  }

  @Override
  public int getPositions() {
    return structType.getPositions();
  }

  @Override
  public ByteArray getByteArray() {
    return bytes;
  }

  @Override
  public void setByteArray(ByteArray bytes) {
    bytes.removeListener(listener);
    this.bytes = bytes;
    bytes.addListener(listener);
  }

  public long getByteLength() {
    return structType.getByteLength(this);
  }

  @Override
  public long getByteLength(int position) {
    return structType.getType(position).getByteLength(this);
  }

  @Override
  public long getArrayLength(int position) {
    return ((ValueType) structType.getType(position)).getArrayLength(this);
  }

  public boolean getBool(int position) {
    return structType.getBool(this, position);
  }

  public boolean getBool(int position, long index) {
    return structType.getBool(this, position, index);
  }

  public Struct setBool(int position, boolean b) {
    structType.setBool(this, position, b);
    return this;
  }

  public Struct setBool(int position, long index, boolean b) {
    structType.setBool(this, position, index, b);
    return this;
  }

  public Struct addBool(int position, boolean b) {
    ((BoolType) structType.getType(position)).add(this, b);
    return this;
  }

  public Struct addBool(int position, long index, boolean b) {
    ((BoolType) structType.getType(position)).add(this, index, b);
    return this;
  }

  public byte getInt8(int position) {
    return structType.getInt8(this, position);
  }

  public byte getInt8(int position, long index) {
     return structType.getInt8(this, position, index);
  }

  public Struct setInt8(int position, byte b) {
    structType.setInt8(this, position, b);
    return this;
  }

  public Struct setInt8(int position, long index, byte b) {
    structType.setInt8(this, position, index, b);
    return this;
  }

  public Struct addInt8(int position, byte b) {
    ((Int8Type) structType.getType(position)).add(this, b);
    return this;
  }

  public Struct addInt8(int position, long index, byte b) {
    ((Int8Type) structType.getType(position)).add(this, index, b);
    return this;
  }

  public short getUint8(int position) {
    return structType.getUint8(this, position);
  }

  public short getUint8(int position, long index) {
    return structType.getUint8(this, position, index);
  }

  public Struct setUint8(int position, short s) {
    structType.setUint8(this, position, s);
    return this;
  }

  public Struct setUint8(int position, long index, short s) {
    structType.setUint8(this, position, index, s);
    return this;
  }

  public Struct addUint8(int position, short s) {
    ((Uint8Type) structType.getType(position)).add(this, s);
    return this;
  }

  public Struct addUint8(int position, long index, short s) {
    ((Uint8Type) structType.getType(position)).add(this, index, s);
    return this;
  }

  public short getInt16(int position) {
    return structType.getInt16(this, position);
  }

  public short getInt16(int position, long index) {
    return structType.getInt16(this, position, index);
  }

  public Struct setInt16(int position, short s) {
    structType.setInt16(this, position, s);
    return this;
  }

  public Struct setInt16(int position, long index, short s) {
    structType.setInt16(this, position, index, s);
    return this;
  }

  public Struct addInt16(int position, short s) {
    ((Int16Type) structType.getType(position)).add(this, s);
    return this;
  }

  public Struct addInt16(int position, long index, short s) {
    ((Int16Type) structType.getType(position)).add(this, index, s);
    return this;
  }

  public int getUint16(int position) {
    return structType.getUint16(this, position);
  }

  public int getUint16(int position, long index) {
    return structType.getUint16(this, position, index);
  }

  public Struct setUint16(int position, int i) {
    structType.setUint16(this, position, i);
    return this;
  }

  public Struct setUint16(int position, long index, int i) {
    structType.setUint16(this, position, index, i);
    return this;
  }

  public Struct addUint16(int position, int i) {
    ((Uint16Type) structType.getType(position)).add(this, i);
    return this;
  }

  public Struct addUint16(int position, long index, int i) {
    ((Uint16Type) structType.getType(position)).add(this, index, i);
    return this;
  }

  public int getInt32(int position) {
    return structType.getInt32(this, position);
  }

  public int getInt32(int position, long index) {
    return structType.getInt32(this, position, index);
  }

  public Struct setInt32(int position, int i) {
    structType.setInt32(this, position, i);
    return this;
  }

  public Struct setInt32(int position, long index, int i) {
    structType.setInt32(this, position, index, i);
    return this;
  }

  public Struct addInt32(int position, int i) {
    ((Int32Type) structType.getType(position)).add(this, i);
    return this;
  }

  public Struct addInt32(int position, long index, int i) {
    ((Int32Type) structType.getType(position)).add(this, index, i);
    return this;
  }

  public long getUint32(int position) {
    return structType.getUint32(this, position);
  }

  public long getUint32(int position, long index) {
    return structType.getUint32(this, position, index);
  }

  public Struct setUint32(int position, long l) {
    structType.setUint32(this, position, l);
    return this;
  }

  public Struct setUint32(int position, long index, long l) {
    structType.setUint32(this, position, index, l);
    return this;
  }

  public Struct addUint32(int position, long l) {
    ((Uint32Type) structType.getType(position)).add(this, l);
    return this;
  }

  public Struct addUint32(int position, long index, long l) {
    ((Uint32Type) structType.getType(position)).add(this, index, l);
    return this;
  }

  public long getInt64(int position) {
    return structType.getInt64(this, position);
  }

  public long getInt64(int position, long index) {
    return structType.getInt64(this, position, index);
  }

  public Struct setInt64(int position, long l) {
    structType.setInt64(this, position, l);
    return this;
  }

  public Struct setInt64(int position, long index, long l) {
    structType.setInt64(this, position, index, l);
    return this;
  }

  public Struct addInt64(int position, long l) {
    ((Int64Type) structType.getType(position)).add(this, l);
    return this;
  }

  public Struct addInt64(int position, long index, long l) {
    ((Int64Type) structType.getType(position)).add(this, index, l);
    return this;
  }

  public BigInteger getUint64(int position) {
    return structType.getUint64(this, position);
  }

  public BigInteger getUint64(int position, long index) {
    return structType.getUint64(this, position, index);
  }

  public Struct setUint64(int position, BigInteger bi) {
    structType.setUint64(this, position, bi);
    return this;
  }

  public Struct setUint64(int position, long index, BigInteger bi) {
    structType.setUint64(this, position, index, bi);
    return this;
  }

  public Struct addUint64(int position, BigInteger bi) {
    ((Uint64Type) structType.getType(position)).add(this, bi);
    return this;
  }

  public Struct addUint64(int position, long index, BigInteger bi) {
    ((Uint64Type) structType.getType(position)).add(this, index, bi);
    return this;
  }

  public float getFloat32(int position) {
    return structType.getFloat32(this, position);
  }

  public float getFloat32(int position, long index) {
    return structType.getFloat32(this, position, index);
  }

  public Struct setFloat32(int position, float f) {
    structType.setFloat32(this, position, f);
    return this;
  }

  public Struct setFloat32(int position, long index, float f) {
    structType.setFloat32(this, position, index, f);
    return this;
  }

  public Struct addFloat32(int position, float f) {
    ((Float32Type) structType.getType(position)).add(this, f);
    return this;
  }

  public Struct addFloat32(int position, long index, float f) {
    ((Float32Type) structType.getType(position)).add(this, index, f);
    return this;
  }

  public double getFloat64(int position) {
    return structType.getFloat64(this, position);
  }

  public double getFloat64(int position, long index) {
    return structType.getFloat64(this, position, index);
  }

  public Struct setFloat64(int position, double d) {
    structType.setFloat64(this, position, d);
    return this;
  }

  public Struct setFloat64(int position, long index, double d) {
    structType.setFloat64(this, position, index, d);
    return this;
  }

  public Struct addFloat64(int position, double d) {
    ((Float64Type) structType.getType(position)).add(this, d);
    return this;
  }

  public Struct addFloat64(int position, long index, double d) {
    ((Float64Type) structType.getType(position)).add(this, index, d);
    return this;
  }

  public Struct getStruct(int position) {
    return structType.getStruct(this, position);
  }

  public Struct setStruct(int position, Struct other) {
    structType.setStruct(this, position, other);
    return this;
  }

  public Struct addStruct(int position, Struct struct) {
     structType.addStruct(position, struct);
     return this;
  }

  public Struct addStruct(int position, long index, Struct struct) {
    structType.addStruct(position, index, struct);
    return this;
  }

  public void removeAll(int position) {
    ((ValueType<?, ?>) structType.getType(position)).remove(this);
  }

  public void remove(int position, long index) {
     assert structType.getType(position) instanceof ValueType<?, ?> : "Field at postion " + position + " is " + structType.getType(position).getClass().getSimpleName() + " but it must be a ValueType";
     if (structType.getType(position) instanceof ValueType<?, ?> valueType) {
       valueType.remove(this, index);
     }
  }

  public Struct with(Consumer<Struct> consumer) {
     consumer.accept(this);
     return this;
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof Struct struct)) return false;

    return offset == struct.offset && structType.equals(struct.structType) && getByteArray().compareBytes(getOffset(), struct.getByteArray(), struct.getOffset(), getByteLength());
  }

  @Override
  public int hashCode() {
    int result = Math.toIntExact(offset);
    result = 31 * result + structType.hashCode();
    for (int i = 0; i < getByteLength(); i++) {
      result = Math.toIntExact(31 * result + bytes.getInt8(getOffset() + i));
    }
    return result;
  }
}
