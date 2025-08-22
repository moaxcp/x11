package com.github.moaxcp.x11.struct;

import java.util.List;

import static com.github.moaxcp.x11.struct.NumberType.Size.BYTE;
import static com.github.moaxcp.x11.struct.NumberType.Size.SHORT;

public class StructTypeBuilder {

  public static StructTypeBuilder structType() {
    return new StructTypeBuilder();
  }
  private int position;
  private Expression lengthExpression;
  private Assignment assignment;
  private List<Type<?>> fields;

  public StructTypeBuilder position(int position) {
    this.position = position;
    return this;
  }

  public StructTypeBuilder lengthExpression(Expression lengthExpression) {
    this.lengthExpression = lengthExpression;
    return this;
  }

  public StructTypeBuilder assignment(Assignment assingment) {
    this.assignment = assingment;
    return this;
  }

  public StructTypeBuilder byteType() {
    fields.add(new NumberType(fields.size(), BYTE));
    return this;
  }

  public StructTypeBuilder byteType(long constantValue) {
    fields.add(new NumberType(fields.size(), BYTE, constantValue, null, null));
    return this;
  }

  public StructTypeBuilder byteArray(int lengthPosition) {
    fields.add(new NumberType(fields.size(), BYTE, null, Expression.valueOf(lengthPosition), Assignment.add(lengthPosition)));
    return this;
  }

  public StructTypeBuilder byteArray(int length, long constantValue) {
    fields.add(new NumberType(fields.size(), BYTE, constantValue, Expression.constant(length), null));
    return this;
  }

  public StructTypeBuilder shortType() {
    fields.add(new NumberType(fields.size(), SHORT));
    return this;
  }

  public StructTypeBuilder shortType(long constantValue) {
    fields.add(new NumberType(fields.size(), SHORT, constantValue, null, null));
    return this;
  }

  public StructTypeBuilder shortArray(int lengthPosition) {
    fields.add(new NumberType(fields.size(), SHORT, null, Expression.valueOf(lengthPosition), Assignment.add(lengthPosition)));
    return this;
  }

  public StructTypeBuilder shortArray(int length, long constantValue) {
    fields.add(new NumberType(fields.size(), SHORT, constantValue, Expression.constant(length), null));
    return this;
  }

  public StructType build() {
    return new StructType(position, lengthExpression, assignment, fields);
  }

  public Struct struct() {
    return new Struct(false, 0, build(), new ByteArray());
  }

  public Struct struct(long offset, ByteArray byteArray) {
    return new Struct(true, offset, build(), byteArray);
  }
}
