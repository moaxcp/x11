package com.github.moaxcp.x11.struct;

import org.jspecify.annotations.Nullable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class PrimitiveBuilder {

  public static PrimitiveBuilder primitive() {
    return new PrimitiveBuilder();
  }

  private int position = -1;
  private final List<ByteLengthChangeListener> byteLengthChangeListeners = new ArrayList<>();
  private final List<ArrayLengthChangeListener> arrayLengthChangeListeners = new ArrayList<>();
  @Nullable
  private Object constantValue;
  @Nullable
  private Expression lengthExpression;

  public PrimitiveBuilder position(int position) {
    this.position = position;
    return this;
  }

  public PrimitiveBuilder byteLengthChange(ByteLengthChangeListener byteLengthChange) {
    byteLengthChangeListeners.add(byteLengthChange);
    return this;
  }

  public PrimitiveBuilder arrayLengthChange(ArrayLengthChangeListener arrayLengthChange) {
    arrayLengthChangeListeners.add(arrayLengthChange);
    return this;
  }

  public PrimitiveBuilder constant(Object constantValue) {
    this.constantValue = constantValue;
    return this;
  }

  public PrimitiveBuilder lengthExpression(Expression lengthExpression) {
    this.lengthExpression = lengthExpression;
    return this;
  }

  private <T> T getConstantValue(Class<T> clazz) {
    return switch (constantValue) {
      case Number n -> {
        if (clazz == Byte.class) {
          yield (T) Byte.valueOf(n.byteValue());
        } else if (clazz == Short.class) {
          yield (T) Short.valueOf(n.shortValue());
        } else if (clazz == Integer.class) {
          yield (T) Integer.valueOf(n.intValue());
        } else if (clazz == Long.class) {
          yield (T) Long.valueOf(n.longValue());
        } else if (clazz == BigInteger.class) {
          yield (T) BigInteger.valueOf(n.longValue());
        } else if (clazz == Float.class) {
          yield (T) Float.valueOf(n.floatValue());
        } else if (clazz == Double.class) {
          yield (T) Double.valueOf(n.doubleValue());
        } else {
          throw new IllegalArgumentException("constant value is not of type " + clazz.getSimpleName() + " but " + n.getClass().getSimpleName());
        }
      }
      case Boolean b -> (T) b;
      case null -> null;
      default -> throw new IllegalStateException("Unexpected value: " + constantValue);
    };
  }

  public BoolType bool() {
    return new BoolType(position, getConstantValue(Boolean.class), lengthExpression)
        .addArrayLengthChangeListeners(arrayLengthChangeListeners)
        .addByteLengthChangeListeners(byteLengthChangeListeners);
  }

  public Int8Type int8() {
    return new Int8Type(position, getConstantValue(Byte.class), lengthExpression)
        .addArrayLengthChangeListeners(arrayLengthChangeListeners)
        .addByteLengthChangeListeners(byteLengthChangeListeners);
  }

  public Uint8Type uint8() {
    return new Uint8Type(position, getConstantValue(Short.class), lengthExpression)
        .addArrayLengthChangeListeners(arrayLengthChangeListeners)
        .addByteLengthChangeListeners(byteLengthChangeListeners);
  }

  public Int16Type int16() {
    return new Int16Type(position, getConstantValue(Short.class), lengthExpression)
        .addArrayLengthChangeListeners(arrayLengthChangeListeners)
        .addByteLengthChangeListeners(byteLengthChangeListeners);
  }

  public Uint16Type uint16() {
    return new Uint16Type(position, getConstantValue(Integer.class), lengthExpression)
        .addArrayLengthChangeListeners(arrayLengthChangeListeners)
        .addByteLengthChangeListeners(byteLengthChangeListeners);
  }

  public Int32Type int32() {
    return new Int32Type(position, getConstantValue(Integer.class), lengthExpression)
        .addArrayLengthChangeListeners(arrayLengthChangeListeners)
        .addByteLengthChangeListeners(byteLengthChangeListeners);
  }

  public Uint32Type uint32() {
    return new Uint32Type(position, getConstantValue(Long.class), lengthExpression)
        .addArrayLengthChangeListeners(arrayLengthChangeListeners)
        .addByteLengthChangeListeners(byteLengthChangeListeners);
  }

  public Int64Type int64() {
    return new Int64Type(position, getConstantValue(Long.class), lengthExpression)
        .addArrayLengthChangeListeners(arrayLengthChangeListeners)
        .addByteLengthChangeListeners(byteLengthChangeListeners);
  }

  public Uint64Type uint64() {
    return new Uint64Type(position, getConstantValue(BigInteger.class), lengthExpression)
        .addArrayLengthChangeListeners(arrayLengthChangeListeners)
        .addByteLengthChangeListeners(byteLengthChangeListeners);
  }

  public Float32Type float32() {
    return new Float32Type(position, getConstantValue(Float.class), lengthExpression)
        .addArrayLengthChangeListeners(arrayLengthChangeListeners)
        .addByteLengthChangeListeners(byteLengthChangeListeners);
  }

  public Float64Type float64() {
    return new Float64Type(position, getConstantValue(Double.class), lengthExpression)
        .addArrayLengthChangeListeners(arrayLengthChangeListeners)
        .addByteLengthChangeListeners(byteLengthChangeListeners);
  }
}
