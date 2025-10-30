package com.github.moaxcp.x11.struct;

import java.util.ArrayList;
import java.util.List;

public abstract class StructTypeBuilder<SELF extends StructTypeBuilder<SELF>> {

  private int position;
  private final List<ByteLengthListener> byteLengthListeners = new ArrayList<>();
  private final List<ArrayLengthListener> arrayLengthListeners = new ArrayList<>();
  private final List<ValueChangeListener> valueChangeListeners = new ArrayList<>();
  private Expression lengthExpression;
  private Struct constant;
  private final List<Type<?>> fields = new ArrayList<>();

  public int fields() {
    return fields.size();
  }

  public Type<?> getField(int position) {
    return fields.get(position);
  }

  public SELF position(int position) {
    this.position = position;
    return (SELF) this;
  }

  public SELF byteLengthListener(ByteLengthListener listener) {
    byteLengthListeners.add(listener);
    return (SELF) this;
  }

  public SELF arrayLengthListener(ArrayLengthListener listener) {
    arrayLengthListeners.add(listener);
    return (SELF) this;
  }

  public SELF valueListener(ValueChangeListener listener) {
    valueChangeListeners.add(listener);
    return (SELF) this;
  }

  public SELF lengthField(int lengthFieldPosition) {
    this.lengthExpression = Expression.valueOf(lengthFieldPosition);
    this.arrayLengthListeners.add(ArrayLengthListener.lengthField(lengthFieldPosition));
    ((ValueType<?, ?>) fields.get(lengthFieldPosition)).addValueChangeListener(ValueChangeListener.extendArrayListener(lengthFieldPosition));
    return (SELF) this;
  }

  public SELF lengthExpression(Expression lengthExpression) {
    this.lengthExpression = lengthExpression;
    return (SELF) this;
  }

  public StructTypePrimitiveSubBuilder<SELF> primitive() {
    return new StructTypePrimitiveSubBuilder<>((SELF) this, fields.size());
  }

  public StructTypePadSubBuilder<SELF> pad() {
    return new StructTypePadSubBuilder<>((SELF) this, fields.size());
  }

  SELF type(Type<?> type) {
    if (type instanceof PadType p && p.isAlign()) {
      var previous = fields.getLast();
      fields.add(type);
      previous.addByteLengthChangeListener(ByteLengthListener.align(type.getPosition()));
    } else {
      fields.add(type);
    }
    return (SELF) this;
  }

  public SELF bool() {
    return primitive().bool();
  }

  public SELF boolArray(int lengthPosition) {
    return primitive().lengthField(lengthPosition).bool();
  }

  public SELF boolArray(Expression expression) {
    return primitive().lengthExpression(expression).bool();
  }

  public SELF int8() {
    return primitive().int8();
  }

  public SELF int8Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).int8();
  }

  public SELF int8Array(Expression expression) {
    return primitive().lengthExpression(expression).int8();
  }

  public SELF uint8() {
    return primitive().uint8();
  }
  public SELF uint8Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).uint8();
  }

  public SELF uint8Array(Expression expression) {
    return primitive().lengthExpression(expression).uint8();
  }

  public SELF int16() {
    return primitive().int16();
  }

  public SELF int16Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).int16();
  }

  public SELF int16Array(Expression expression) {
    return primitive().lengthExpression(expression).int16();
  }

  public SELF uint16() {
    return primitive().uint16();
  }

  public SELF uint16Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).uint16();
  }

  public SELF uint16Array(Expression expression) {
    return primitive().lengthExpression(expression).uint16();
  }

  public SELF int32() {
    return primitive().int32();
  }

  public SELF int32Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).int32();
  }

  public SELF int32Array(Expression expression) {
    return primitive().lengthExpression(expression).int32();
  }
  public SELF uint32() {
    return primitive().uint32();
  }

  public SELF uint32Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).uint32();
  }

  public SELF uint32Array(Expression expression) {
    return primitive().lengthExpression(expression).uint32();
  }

  public SELF int64() {
    return primitive().int64();
  }

  public SELF int64Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).int64();
  }

  public SELF int64Array(Expression expression) {
    return primitive().lengthExpression(expression).int64();
  }

  public SELF uint64() {
    return primitive().uint64();
  }
  public SELF uint64Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).uint64();
  }

  public SELF uint64Array(Expression expression) {
    return primitive().lengthExpression(expression).uint64();
  }

  public SELF float32() {
    return primitive().float32();
  }

  public SELF float32Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).float32();
  }

  public SELF float32Array(Expression expression) {
    return primitive().lengthExpression(expression).float32();
  }

  public SELF float64() {
    return primitive().float64();
  }

  public SELF float64Array(int lengthPosition) {
    return primitive().lengthField(lengthPosition).float64();
  }

  public SELF float64Array(Expression expression) {
    return primitive().lengthExpression(expression).float64();
  }

  public SELF pad(long length) {
    return pad().length(length).pad();
  }

  public SELF align(long length) {
    return pad().length(length).align();
  }

  public ChildStructTypeBuilder<SELF> struct() {
    return new ChildStructTypeBuilder<>((SELF) this, fields.size());
  }

  public ChildStructTypeBuilder<SELF> structArray(int lengthPosition) {
    return new ChildStructTypeBuilder<>((SELF) this, fields.size()).lengthField(lengthPosition);
  }

  public SELF constant(Struct constant) {
    this.constant = constant;
    return (SELF) this;
  }

  public StructType toStructType() {
    var type = new StructType(position, constant, lengthExpression, fields);
    type.addByteLengthChangeListeners(byteLengthListeners);
    type.addArrayLengthChangeListeners(arrayLengthListeners);
    type.addValueChangeListeners(valueChangeListeners);
    return type;
  }
}
