package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

public class NumberType extends Type<Long> {

  public static NumberType byteType() {
    return new NumberType(-1, Size.BYTE);
  }

  public static NumberType byteType(int position) {
    return new NumberType(position, Size.BYTE);
  }

  public static NumberType byteArray(int position, int lengthPosition) {
    return new NumberType(position, Size.BYTE, null, Expression.valueOf(lengthPosition), Assignment.add(lengthPosition));
  }

  public static NumberType uByteType() {
    return new NumberType(-1, Size.UBYTE);
  }

  public static NumberType uByteType(int position) {
    return new NumberType(position, Size.UBYTE);
  }

  public static NumberType shortType() {
    return new NumberType(-1, Size.SHORT);
  }

  public static NumberType shortType(int position) {
    return new NumberType(position, Size.SHORT);
  }

  public static NumberType shortArray(int position, int lengthPosition) {
    return new NumberType(position, Size.SHORT, null, Expression.valueOf(lengthPosition), Assignment.add(lengthPosition));
  }

  public static NumberType uShortType() {
    return new NumberType(-1, Size.USHORT);
  }

  public static NumberType intType() {
    return new NumberType(-1, Size.INT);
  }

  public static NumberType intType(int position) {
    return new NumberType(position, Size.INT);
  }
  public static NumberType uIntType() {
    return new NumberType(-1, Size.UINT);
  }

  public static NumberType uIntType(int position) {
    return new NumberType(position, Size.UINT);
  }

  public static NumberType longType() {
    return new NumberType(-1, Size.LONG);
  }

  public static NumberType longType(int position) {
    return new NumberType(position, Size.LONG);
  }

  public static NumberType uLongType() {
    return new NumberType(-1, Size.ULONG);
  }

  public static NumberType uLongType(int position) {
    return new NumberType(position, Size.ULONG);
  }
  public static NumberType floatType() {
    return new NumberType(-1, Size.FLOAT);
  }

  public static NumberType floatType(int position) {
    return new NumberType(position, Size.FLOAT);
  }

  public static NumberType doubleType() {
    return new NumberType(-1, Size.DOUBLE);
  }

  public static NumberType doubleType(int position) {
    return new NumberType(position, Size.DOUBLE);
  }

  public enum Size {
    BYTE(1),
    UBYTE(1),
    SHORT(2),
    USHORT(2),
    INT(4),
    UINT(4),
    LONG(8),
    ULONG(8),
    FLOAT(4),
    DOUBLE(8);

    private final int size;

    Size(int size) {
      this.size = size;
    }

    public int size() {
      return size;
    }
  }
  protected Size unitSize;

  public NumberType(int position, Size size) {
    super(position);
    this.unitSize = size;
  }

  NumberType(int position, Size unitSize, Long constantValue, @Nullable Expression lengthExpression, @Nullable Assignment assignment) {
    super(position, constantValue, lengthExpression, assignment);
    this.unitSize = unitSize;
    this.constantValue = constantValue;
  }

  @Override
  protected Type<Long> copy(int position) {
    return new NumberType(position, unitSize, constantValue, lengthExpression, assignment);
  }

  public Size getUnitSize() {
    return unitSize;
  }

  @Override
  public final long getByteLength(Pointer<?, ? extends Type<?>> pointer, long index) {
    return getUnitSize().size();
  }

  @Override
  public boolean isFixedLength(Pointer<?, ? extends Type<?>> pointer) {
    return lengthExpression == null || lengthExpression.isConstant(pointer);
  }

  @Override
  public Long get(Pointer<?, ? extends Type<?>> pointer, long index) {
    return switch (unitSize) {
      case BYTE -> pointer.getByteArray().getByte(getOffset(pointer, index));
      case SHORT -> pointer.getByteArray().getShort(getOffset(pointer, index));
      case null, default -> throw new IllegalArgumentException("unitSize: " + unitSize + " is not supported");
    };
  }

  @Override
  public void set(Pointer<?, ? extends Type<?>> pointer, long index, Long value) {
    switch (unitSize) {
      case BYTE -> pointer.getByteArray().setByte(getOffset(pointer, index), (byte) (long) value);
      case SHORT -> pointer.getByteArray().setShort(getOffset(pointer, index), (short) (long) value);
      case null, default -> throw new IllegalArgumentException("unitSize: " + unitSize + " is not supported");
    }
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer, long index) {
    long value = constantValue != null ? constantValue : 0;
    switch (unitSize) {
      case BYTE -> pointer.getByteArray().addByte(getOffset(pointer, index), (byte) value);
      case SHORT -> pointer.getByteArray().addShort(getOffset(pointer, index), (short) value);
      case null, default -> throw new IllegalArgumentException("unitSize: " + unitSize + " is not supported");
    }
  }
}
