package com.github.moaxcp.x11.struct;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StructType extends Type<Struct> {

  protected final List<Type<?>> fields;

  protected StructType(int position, List<Type<?>> fields) {
    super(position);
    this.fields = fields;
  }

  private StructType(int position, StructType structType) {
    super(position);
    fields = new ArrayList<>(structType.fields);
  }

  StructType(int position, Expression lengthExpression, Assignment assignment, List<Type<?>> fields) {
    super(position, null, lengthExpression, assignment);
    this.fields = fields;
  }

  @Override
  public StructType copy(int position) {
    return new StructType(position, this);
  }

  public <V extends Type<?>> V getType(int position) {
    return (V) fields.get(position);
  }

  @Override
  public long getByteLength(Pointer<?, ? extends Type<?>> pointer, long index) {
    var struct = get(pointer, index);
    return struct.getByteLength();
  }

  @Override
  public boolean isFixedLength(Pointer<?, ? extends Type<?>> pointer) {
    for (int i = 0; i < fields.size(); i++) {
      var type = fields.get(i);
      if(!type.isFixedLength(pointer)) {
        return false;
      }
    }
    return lengthExpression == null || lengthExpression.isConstant(pointer);
  }

  @Override
  public Struct get(Pointer<?, ? extends Type<?>> pointer, long index) {
    var offset = getOffset(pointer, index);
    return new Struct(offset, this, pointer.getByteArray());
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Struct value) {
    var struct = get(pointer, index);
    for (int i = 0; i < fields.size(); i++) {
      var pointerField = ((Type) fields.get(i));
      var structField = struct.getType(i);
      for (int j = 0; j < structField.getArrayLength(struct); j++) {
        pointerField.set(pointer, j, structField.get(struct, j));
      }
    }
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    var struct = get(pointer, index);
    for (int i = 0; i < fields.size(); i++) {
      struct.getType(i).allocate(pointer);
    }
  }

  public long getByte(Struct struct, int position) {
    return ((NumberType) fields.get(position)).get(struct);
  }

  public long getByte(Struct struct, int position, long index) {
    return ((NumberType) fields.get(position)).get(struct, index);
  }

  public void setByte(Struct struct, int position, long b) {
    ((NumberType) fields.get(position)).set(struct, b);
  }

  public long getShort(Struct struct, int position) {
    return ((NumberType) fields.get(position)).get(struct);
  }

  public long getShort(Struct struct, int position, long index) {
    return ((NumberType) fields.get(position)).get(struct, index);
  }

  public void setShort(Struct struct, int position, long s) {
    ((NumberType) fields.get(position)).set(struct, s);
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
