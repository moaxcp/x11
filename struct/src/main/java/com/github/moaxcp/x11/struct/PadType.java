package com.github.moaxcp.x11.struct;

public final class PadType extends Type<PadType> {

  public static PadType pad(long length) {
    return new PadType(-1, length, false);
  }

  public static PadType pad(int position, long length) {
    return new PadType(position, length, false);
  }

  public static PadType align(long length) {
    return new PadType(-1, length, true);
  }

  public static PadType align(int position, long length) {
    return new PadType(position, length, true);
  }

  private final long length;
  private final boolean align;

  public PadType(int position, long length, boolean align) {
    super(position);
    this.length = length;
    this.align = align;
  }

  @Override
  protected PadType copy(int position) {
    return new PadType(position, this.length, this.align);
  }

  @Override
  public long getAllocationLength(Type<?> parent) {
    if (!align) {
      return length;
    }

    return length - parent.getType(position - 1).getAllocationLength(parent) % length;
  }

  public long getPadLength() {
    return length;
  }

  public boolean isAlign() {
    return align;
  }

  @Override
  public long getByteLength(Pointer<?, ? extends Type<?>> pointer) {
    if (!align) {
      return length;
    }
    return length - pointer.getByteLength(position - 1) % length;
  }

  @Override
  public boolean isFixedLength(Pointer<?, ? extends Type<?>> pointer) {
    return !align || pointer.getType(position - 1).isFixedLength(pointer);
  }

  @Override
  public void allocate(Pointer<?, ? extends Type<?>> pointer) {
    var padLength = getByteLength(pointer);
    for(long i = 0; i < padLength; i++) {
      pointer.getByteArray().addInt8(getOffset(pointer) + i, (byte) 0);
    }
  }

  public void reAlign(Pointer<?, ? extends Type<?>> pointer, long previousLength, long currentLength) {
    assert align : "PadType is not aligned";
    previousLength = length - previousLength % length;
    currentLength = length - currentLength % length;
    if(currentLength > previousLength) {
      var padLength = currentLength - previousLength;
      for(long i = 0; i < padLength; i++) {
        pointer.getByteArray().addInt8(getOffset(pointer) + i, (byte) 0);
      }
    } else if(currentLength < previousLength) {
      var padLength = previousLength - currentLength;
      for(long i = 0; i < padLength; i++) {
        pointer.getByteArray().removeInt8(getOffset(pointer));
      }
    }
    notifyByteLengthChange(pointer, previousLength, currentLength);
  }

  @Override
  public String toString() {
    return "PadType{" +
        "length=" + length +
        ", align=" + align +
        ", position=" + position +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    PadType padType = (PadType) o;
    return length == padType.length && align == padType.align;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + Long.hashCode(length);
    result = 31 * result + Boolean.hashCode(align);
    return result;
  }
}
