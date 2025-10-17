package com.github.moaxcp.x11.struct;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class StructType extends ValueType<Struct> {

  private final List<Type> fields;

  private StructType(int position, StructType structType) {
    super(position);
    fields = new ArrayList<>(structType.fields);
  }

  StructType(int position, Struct constant, Expression lengthExpression, Assignment assignment, List<Type> fields) {
    super(position, constant, lengthExpression, assignment);
    this.fields = fields;
  }

  @Override
  public StructType copy(int position) {
    return new StructType(position, this);
  }

  public <V extends Type> V getType(int position) {
    return (V) fields.get(position);
  }

  public int getPositions() {
    return fields.size();
  }

  @Override
  public long getByteLength(Pointer<?, ? extends Type> pointer, long index) {
    var struct = get(pointer, index);
    return struct.getByteLength();
  }

  @Override
  public boolean isFixedLength(Pointer<?, ? extends Type> pointer) {
    for (int i = 0; i < fields.size(); i++) {
      var type = fields.get(i);
      if(!type.isFixedLength(pointer)) {
        return false;
      }
    }
    return lengthExpression == null || lengthExpression.isConstant(pointer);
  }

  @Override
  public Struct get(Pointer<?, ? extends Type> pointer, long index) {
    var offset = getOffset(pointer, index);
    return new Struct(offset, this, pointer.getByteArray());
  }

  @Override
  public void set(Pointer<?, ? extends Type> pointer, long index, Struct value) {
    var struct = get(pointer, index);
    for (int i = 0; i < fields.size(); i++) {
      var pointerField = fields.get(i);
      var structField = struct.getType(i);
      assert pointerField.getClass().equals(structField.getClass()) : "pointerField and structField must be the same type";
      switch (structField) {
        case ValueType<?> valueType -> {
          for (int j = 0; j < valueType.getArrayLength(struct); j++) {
            switch (valueType) {
              case PrimitiveType<?> v -> {
              }
              case StructType structType -> {
                structType.set(pointer, j, structType.get(struct, j));
              }
            }
          }
        }
        case PadType padType -> {
        }
      }
    }
  }

  @Override
  public void allocate(Pointer<?, ? extends Type> pointer, long index) {
    var struct = get(pointer, index);
    for (int i = 0; i < fields.size(); i++) {
      struct.getType(i).allocate(pointer);
    }
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
    return ((Uint64Type) fields.get(position)).get(struct);
  }

  public void setUint64(Struct struct, int position, BigInteger bi) {
    ((Uint64Type) fields.get(position)).set(struct, bi);
  }

  public BigInteger getUint64(Struct struct, int position, long index) {
    return ((Uint64Type) fields.get(position)).get(struct, index);
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

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof StructType that)) return false;

    return position == that.position && Objects.equals(fields, that.fields);
  }

  @Override
  public int hashCode() {
    int result = position;
    result = 31 * result + Objects.hashCode(fields);
    return result;
  }
}
