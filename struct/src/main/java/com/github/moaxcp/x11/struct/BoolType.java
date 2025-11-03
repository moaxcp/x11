package com.github.moaxcp.x11.struct;

import com.github.moaxcp.x11.struct.ArrayLengthListener.ArrayLengthReason;
import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Primitive.BOOL;

/**
 * Boolean type backed by a single byte in ByteArray.
 * Primitive boolean API only; wrapper-based Type methods throw just like NumberType primitives do.
 */
public final class BoolType extends PrimitiveType<BoolType, Boolean> {

  public static BoolType bool() {
    return bool(-1);
  }

  public static BoolType bool(int position) {
    return new BoolType(position);
  }

  public BoolType(int position, @Nullable Boolean constantValue, @Nullable Expression lengthExpression) {
    super(position, BOOL, constantValue, lengthExpression);
  }

  public BoolType(int position) {
    super(position, BOOL);
  }

  @Override
  protected BoolType copy(int position) {
    return new BoolType(position, constantValue, lengthExpression);
  }

  public boolean getBoolean(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().getBool(getOffset(pointer));
  }

  public boolean getBoolean(Pointer<?, ? extends Type<?>> pointer, long index) {
    checkIndex(pointer, index);
    return pointer.getByteArray().getBool(getOffset(pointer, index));
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, boolean value) {
    setUnchecked(pointer, 0, value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, boolean value) {
    checkIndex(pointer, index);
    setUnchecked(pointer, index, value);
  }

  private void setUnchecked(Pointer<?, ? extends Type<?>> pointer, long index, boolean value) {
    checkConstant(pointer, index, value);
    pointer.getByteArray().setBool(getOffset(pointer, index), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, boolean value) {
    add(pointer, getArrayLength(pointer), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, boolean value) {
    if (!isArray()) {
      throw new ArrayIndexOutOfBoundsException(getClass().getSimpleName() + " cannot add to non-array type at position " + getPosition() + " index: " + index + " length: " + 1);
    }
    allocate(pointer, index);
    setUnchecked(pointer, index, value);
  }

  public void allocate(Pointer<?, ? extends Type<?>> pointer) {
    if (isArray()) {
      long length = getArrayLength(pointer);
      for (int i = 0; i < length; i++) {
        pointer.getByteArray().addBool(getOffset(pointer, i), constantValue != null ? constantValue : false);
      }
    } else {
      pointer.getByteArray().addBool(getOffset(pointer, 0), constantValue != null ? constantValue : false);
    }
  }

  @Override
  protected void allocate(ArrayLengthReason reason, Pointer<?, ? extends Type<?>> pointer, long index) {
    callWithArrayLengthChange(reason, pointer, 1, () -> {
      callWithByteLengthChange(pointer, () -> {
        checkIndexAllocate(pointer, index);
        pointer.getByteArray().addBool(getOffset(pointer, index), constantValue != null ? constantValue : false);
      });
    });
  }
}
