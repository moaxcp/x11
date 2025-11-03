package com.github.moaxcp.x11.struct;

import com.github.moaxcp.x11.struct.ArrayLengthListener.ArrayLengthReason;
import org.jspecify.annotations.Nullable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason.SET_VALUE;

public final class StructType extends ValueType<StructType, Struct> {

  private final List<Type<?>> fields;

  StructType(int position, @Nullable Struct constant, @Nullable Expression lengthExpression, List<Type<?>> fields) {
    super(position, constant, lengthExpression);
    this.fields = fields;
  }

  @Override
  public StructType copy(int position) {
    return new StructType(position, this.constantValue, this.lengthExpression, new ArrayList<>(fields));
  }

  public <V extends Type<?>> V getType(int position) {
    return (V) fields.get(position);
  }

  @Override
  public long getAllocationLength(Type<?> parent) {
    return fields.stream().mapToLong(f -> getAllocationLength(parent)).sum();
  }

  public int getPositions() {
    return fields.size();
  }

  @Override
  public long getByteLength(Pointer<?, ? extends Type<?>> pointer, long index) {
    long byteLength = 0;
    var length = getArrayLength(pointer);
    for (long i = 0; i < length; i++) {
      var struct = new Struct(getOffset(pointer, i), this, pointer.getByteArray());
      for (int j = 0; j < fields.size(); j++) {
        var field = fields.get(j);
        byteLength += field.getByteLength(struct);
      }
    }
    return byteLength;
  }

  @Override
  public boolean isFixedLength(Pointer<?, ? extends Type<?>> pointer) {
    for (int i = 0; i < fields.size(); i++) {
      var type = fields.get(i);
      if(!type.isFixedLength(pointer)) {
        return false;
      }
    }
    return lengthExpression == null || lengthExpression.isConstant(pointer.getType());
  }

  @Override
  public Struct get(Pointer<?, ? extends Type<?>> pointer, long index) {
    var offset = getOffset(pointer, index);
    return new Struct(offset, this, pointer.getByteArray());
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Struct value) {
    if (!valueChangeListeners.isEmpty()) {
      var old = new Struct(getOffset(pointer, index), this, pointer.getByteArray());
      pointer.getByteArray().replace(getOffset(pointer, index), getByteLength(pointer, index), value.getByteArray(), value.getOffset(), value.getByteLength());
      notifyValueChange(SET_VALUE, pointer, index, old, value);
    }
    pointer.getByteArray().replace(getOffset(pointer, index), getByteLength(pointer, index), value.getByteArray(), value.getOffset(), value.getByteLength());
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer) {
    if (isArray()) {
      var length = getArrayLength(pointer);
      for (long $ = 0; $ < length; $++) {
        for (int i = 0; i < fields.size(); i++) {
          var type = fields.get(i);
          if (type instanceof StructType structType) {
            new Struct(false, type.getOffset(pointer), structType, pointer.getByteArray());
          } else {
            fields.get(i).allocate(pointer);
          }
        }
      }
    }
    for (int i = 0; i < fields.size(); i++) {
      var type = fields.get(i);
      if (type instanceof StructType structType) {
        new Struct(false, type.getOffset(pointer), structType, pointer.getByteArray());
      } else {
        fields.get(i).allocate(pointer);
      }
    }
  }

  @Override
  protected void allocate(ArrayLengthReason reason, Pointer<?, ? extends Type<?>> pointer, long index) {
    callWithArrayLengthChange(reason, pointer, 1, () -> {
      callWithByteLengthChange(pointer, () -> {
        var struct = new Struct(false, getOffset(pointer, index), this, pointer.getByteArray());
        for (int i = 0; i < fields.size(); i++) {
          struct.getType(i).allocate(pointer);
        }
      });
    });
  }

  public boolean getBool(Struct struct, int position) {
    return ((BoolType) fields.get(position)).getBoolean(struct);
  }

  public void setBool(Struct struct, int position, boolean value) {
    ((BoolType) fields.get(position)).set(struct, value);
  }

  public boolean getBool(Struct struct, int position, long index) {
    return ((BoolType) fields.get(position)).getBoolean(struct, index);
  }

  public void setBool(Struct struct, int position, long index, boolean value) {
    ((BoolType) fields.get(position)).set(struct, index, value);
  }

  public byte getInt8(Struct struct, int position) {
    return ((Int8Type) fields.get(position)).getInt8(struct);
  }

  public void setInt8(Struct struct, int position, byte b) {
    ((Int8Type) fields.get(position)).set(struct, b);
  }

  public byte getInt8(Struct struct, int position, long index) {
    return ((Int8Type) fields.get(position)).getInt8(struct, index);
  }

  public void setInt8(Struct struct, int position, long index, byte b) {
    ((Int8Type) fields.get(position)).set(struct, index, b);
  }
  
  public short getUint8(Struct struct, int position) {
    return ((Uint8Type) fields.get(position)).getUint8(struct);
  }

  public void setUint8(Struct struct, int position, short s) {
    ((Uint8Type) fields.get(position)).set(struct, s);
  }

  public short getUint8(Struct struct, int position, long index) {
    return ((Uint8Type) fields.get(position)).getUint8(struct, index);
  }

  public void setUint8(Struct struct, int position, long index, short s) {
    ((Uint8Type) fields.get(position)).set(struct, index, s);
  }

  public short getInt16(Struct struct, int position) {
    return ((Int16Type) fields.get(position)).getInt16(struct);
  }

  public void setInt16(Struct struct, int position, short s) {
    ((Int16Type) fields.get(position)).set(struct, s);
  }

  public short getInt16(Struct struct, int position, long index) {
    return ((Int16Type) fields.get(position)).getInt16(struct, index);
  }

  public void setInt16(Struct struct, int position, long index, short s) {
    ((Int16Type) fields.get(position)).set(struct, index, s);
  }

  public int getUint16(Struct struct, int position) {
    return ((Uint16Type) fields.get(position)).getUint16(struct);
  }

  public void setUint16(Struct struct, int position, int i) {
    ((Uint16Type) fields.get(position)).set(struct, i);
  }

  public int getUint16(Struct struct, int position, long index) {
    return ((Uint16Type) fields.get(position)).getUint16(struct, index);
  }

  public void setUint16(Struct struct, int position, long index, int i) {
    ((Uint16Type) fields.get(position)).set(struct, index, i);
  }

  public int getInt32(Struct struct, int position) {
    return ((Int32Type) fields.get(position)).getInt32(struct);
  }

  public void setInt32(Struct struct, int position, int i) {
    ((Int32Type) fields.get(position)).set(struct, i);
  }

  public int getInt32(Struct struct, int position, long index) {
    return ((Int32Type) fields.get(position)).getInt32(struct, index);
  }

  public void setInt32(Struct struct, int position, long index, int i) {
    ((Int32Type) fields.get(position)).set(struct, index, i);
  }

  public long getUint32(Struct struct, int position) {
    return ((Uint32Type) fields.get(position)).getUint32(struct);
  }

  public void setUint32(Struct struct, int position, long l) {
    ((Uint32Type) fields.get(position)).set(struct, l);
  }

  public long getUint32(Struct struct, int position, long index) {
    return ((Uint32Type) fields.get(position)).getUint32(struct, index);
  }

  public void setUint32(Struct struct, int position, long index, long l) {
    ((Uint32Type) fields.get(position)).set(struct, index, l);
  }

  public long getInt64(Struct struct, int position) {
    return ((Int64Type) fields.get(position)).getInt64(struct);
  }

  public void setInt64(Struct struct, int position, long l) {
    ((Int64Type) fields.get(position)).set(struct, l);
  }

  public long getInt64(Struct struct, int position, long index) {
    return ((Int64Type) fields.get(position)).getInt64(struct, index);
  }

  public void setInt64(Struct struct, int position, long index, long l) {
    ((Int64Type) fields.get(position)).set(struct, index, l);
  }

  public BigInteger getUint64(Struct struct, int position) {
    return ((Uint64Type) fields.get(position)).getUint64(struct);
  }

  public void setUint64(Struct struct, int position, BigInteger bi) {
    ((Uint64Type) fields.get(position)).set(struct, bi);
  }

  public BigInteger getUint64(Struct struct, int position, long index) {
    return ((Uint64Type) fields.get(position)).getUint64(struct, index);
  }

  public void setUint64(Struct struct, int position, long index, BigInteger bi) {
    ((Uint64Type) fields.get(position)).set(struct, index, bi);
  }

  public float getFloat32(Struct struct, int position) {
    return ((Float32Type) fields.get(position)).getFloat32(struct);
  }

  public void setFloat32(Struct struct, int position, float f) {
    ((Float32Type) fields.get(position)).set(struct, f);
  }

  public float getFloat32(Struct struct, int position, long index) {
    return ((Float32Type) fields.get(position)).getFloat32(struct, index);
  }

  public void setFloat32(Struct struct, int position, long index, float f) {
    ((Float32Type) fields.get(position)).set(struct, index, f);
  }

  public double getFloat64(Struct struct, int position) {
    return ((Float64Type) fields.get(position)).getFloat64(struct);
  }

  public void setFloat64(Struct struct, int position, double d) {
    ((Float64Type) fields.get(position)).set(struct, d);
  }

  public double getFloat64(Struct struct, int position, long index) {
    return ((Float64Type) fields.get(position)).getFloat64(struct, index);
  }

  public void setFloat64(Struct struct, int position, long index, double d) {
    ((Float64Type) fields.get(position)).set(struct, index, d);
  }

  public Struct getStruct(Struct struct, int position) {
    return ((StructType) fields.get(position)).get(struct);
  }

  public void setStruct(Struct struct, int position, Struct other) {
    ((StructType) fields.get(position)).set(struct, other);
  }

  public void addStruct(int position, Struct struct) {
    ((StructType) fields.get(position)).addStruct(position, struct);
  }

  public void addStruct(int position, long index, Struct struct) {
    ((StructType) fields.get(position)).addStruct(position, index, struct);
  }

  @Override
  public String toString() {
    return "StructType{" +
        "fields=" + fields +
        ", lengthExpression=" + lengthExpression +
        ", constantValue=" + constantValue +
        ", position=" + position +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    StructType that = (StructType) o;
    return fields.equals(that.fields);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + fields.hashCode();
    return result;
  }
}
