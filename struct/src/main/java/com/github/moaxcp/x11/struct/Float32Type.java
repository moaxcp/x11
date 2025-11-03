package com.github.moaxcp.x11.struct;

import com.github.moaxcp.x11.struct.ArrayLengthListener.ArrayLengthReason;
import com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason;
import org.jspecify.annotations.Nullable;

import static com.github.moaxcp.x11.struct.Primitive.FLOAT32;
import static com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason.SET_BY_ARRAY_LENGTH;
import static com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason.SET_VALUE;

public final class Float32Type extends NumberType<Float32Type, Float> {

  public static Float32Type float32Type() {
    return float32Type(-1);
  }

  public static Float32Type float32Type(int position) {
    return new Float32Type(position);
  }

  public Float32Type(int position, @Nullable Float constantValue, @Nullable Expression lengthExpression) {
    super(position, FLOAT32, constantValue, lengthExpression);
  }

  public Float32Type(int position) {
    super(position, FLOAT32);
  }

  @Override
  protected Float32Type copy(int position) {
    return new Float32Type(position, constantValue, lengthExpression);
  }

  public float getFloat32(Pointer<?, ? extends Type<?>> pointer) {
    return pointer.getByteArray().getFloat32(getOffset(pointer));
  }

  public float getFloat32(Pointer<?, ? extends Type<?>> pointer, long index) {
    checkIndex(pointer, index);
    return pointer.getByteArray().getFloat32(getOffset(pointer, index));
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, float value) {
    setUnchecked(SET_VALUE, pointer, 0, value);
  }

  void setForArrayLength(Pointer<?, ? extends Type<?>> pointer, long value) {
    setUnchecked(SET_BY_ARRAY_LENGTH, pointer, 0, (float) value);
  }

  public void set(Pointer<?, ? extends Type<?>> pointer, long index, float value) {
    checkIndex(pointer, index);
    setUnchecked(SET_VALUE, pointer, index, value);
  }

  private void setUnchecked(ValueChangeReason reason, Pointer<?, ? extends Type<?>> pointer, long index, float value) {
    checkConstant(pointer, index, value);
    if (!valueChangeListeners.isEmpty()) {
      var old = pointer.getByteArray().getFloat32(getOffset(pointer, index));
      pointer.getByteArray().setFloat32(getOffset(pointer, index), value);
      notifyValueChange(reason, pointer, index, old, value);
    }
    pointer.getByteArray().setFloat32(getOffset(pointer, index), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, float value) {
    add(pointer, getArrayLength(pointer), value);
  }

  public void add(Pointer<?, ? extends Type<?>> pointer, long index, float value) {
    if (!isArray()) {
      throw new ArrayIndexOutOfBoundsException(getClass().getSimpleName() + " cannot add to non-array type at position " + getPosition() + " index: " + index + " length: " + 1);
    }
    allocate(pointer, index);
    setUnchecked(SET_VALUE, pointer, index, value);
  }

  public void allocate(Pointer<?, ? extends Type<?>> pointer) {
    if (isArray()) {
      long length = getArrayLength(pointer);
      for (int i = 0; i < length; i++) {
        pointer.getByteArray().addFloat32(getOffset(pointer, i), constantValue != null ? constantValue : 0.0f);
      }
    } else {
      pointer.getByteArray().addFloat32(getOffset(pointer, 0), constantValue != null ? constantValue : 0.0f);
    }
  }

  @Override
  protected void allocate(ArrayLengthReason reason, Pointer<?, ? extends Type<?>> pointer, long index) {
    callWithArrayLengthChange(reason, pointer, 1, () -> {
      callWithByteLengthChange(pointer, () -> {
        checkIndexAllocate(pointer, index);
        pointer.getByteArray().addFloat32(getOffset(pointer, index), constantValue != null ? constantValue : 0.0f);
      });
    });
  }
}
