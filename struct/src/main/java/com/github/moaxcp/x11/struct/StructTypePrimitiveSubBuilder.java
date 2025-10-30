package com.github.moaxcp.x11.struct;

public class StructTypePrimitiveSubBuilder<PARENT extends StructTypeBuilder<PARENT>> {
  private final PARENT structTypeBuilder;
  private final PrimitiveBuilder primitiveBuilder;

  StructTypePrimitiveSubBuilder(PARENT structTypeBuilder, int position) {
    this.structTypeBuilder = structTypeBuilder;
    primitiveBuilder = new PrimitiveBuilder().position(position);
  }

  public StructTypePrimitiveSubBuilder<PARENT> byteLengthChange(ByteLengthListener byteLengthChange) {
    primitiveBuilder.byteLengthListener(byteLengthChange);
    return this;
  }

  public StructTypePrimitiveSubBuilder<PARENT> constant(Object constantValue) {
    primitiveBuilder.constant(constantValue);
    return this;
  }

  public StructTypePrimitiveSubBuilder<PARENT> lengthField(int lengthFieldPosition) {
    primitiveBuilder.lengthExpression(Expression.valueOf(lengthFieldPosition));
    primitiveBuilder.arrayLengthListener(ArrayLengthListener.lengthField(lengthFieldPosition));
    ((ValueType<?, ?>) structTypeBuilder.getField(lengthFieldPosition)).addValueChangeListener(ValueChangeListener.extendArrayListener(structTypeBuilder.fields()));
    return this;
  }

  public StructTypePrimitiveSubBuilder<PARENT> lengthExpression(Expression lengthExpression) {
    primitiveBuilder.lengthExpression(lengthExpression);
    return this;
  }

  public StructTypePrimitiveSubBuilder<PARENT> arrayLengthListener(ArrayLengthListener arrayLengthListener) {
    primitiveBuilder.arrayLengthListener(arrayLengthListener);
    return this;
  }

  public StructTypePrimitiveSubBuilder<PARENT> byteLengthListener(ByteLengthListener byteLengthListener) {
    primitiveBuilder.byteLengthListener(byteLengthListener);
    return this;
  }

  public StructTypePrimitiveSubBuilder<PARENT> valueListener(ValueChangeListener valueChangeListener) {
    primitiveBuilder.valueListener(valueChangeListener);
    return this;
  }

  public PARENT bool() {
    return structTypeBuilder.type(primitiveBuilder.bool());
  }

  public PARENT int8() {
    return structTypeBuilder.type(primitiveBuilder.int8());
  }

  public PARENT uint8() {
    return structTypeBuilder.type(primitiveBuilder.uint8());
  }

  public PARENT int16() {
    return structTypeBuilder.type(primitiveBuilder.int16());
  }

  public PARENT uint16() {
    return structTypeBuilder.type(primitiveBuilder.uint16());
  }

  public PARENT int32() {
    return structTypeBuilder.type(primitiveBuilder.int32());
  }

  public PARENT uint32() {
    return structTypeBuilder.type(primitiveBuilder.uint32());
  }

  public PARENT int64() {
    return structTypeBuilder.type(primitiveBuilder.int64());
  }

  public PARENT uint64() {
    return structTypeBuilder.type(primitiveBuilder.uint64());
  }

  public PARENT float32() {
    return structTypeBuilder.type(primitiveBuilder.float32());
  }

  public PARENT float64() {
    return structTypeBuilder.type(primitiveBuilder.float64());
  }
}
