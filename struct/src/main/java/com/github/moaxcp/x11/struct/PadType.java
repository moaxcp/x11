package com.github.moaxcp.x11.struct;

public final class PadType extends Type {

  private final long length;
  private final boolean align;

  public PadType(int position, long length, boolean align) {
    super(position);
    this.length = length;
    this.align = align;
  }

  @Override
  protected Type copy(int position) {
    return new PadType(position, this.length, this.align);
  }

  @Override
  public long getByteLength(Pointer<?, ? extends Type> pointer) {
    return length;
  }

  @Override
  public boolean isFixedLength(Pointer<?, ? extends Type> pointer) {
    return !align;
  }

  public void reAlign(Pointer<?, ? extends Type> pointer, long previousLength, long currentLength) {
    assert !align : "PadType is not aligned";
    if(currentLength > previousLength) {
      var padLength = currentLength - previousLength;
      for(long i = 0; i < padLength; i++) {
        pointer.getByteArray().addInt8(getOffset(pointer) + i, (byte) 0);
      }
    } else if(currentLength < previousLength) {
      var padLength = previousLength - currentLength;
      for(long i = 0; i < padLength; i++) {
        pointer.getByteArray().removeInt8(getOffset(pointer) + i);
      }
    }
  }

  @Override
  public void allocate(Pointer<?, ? extends Type> pointer) {
    var padLength = length;
    if (align) {
      padLength = pointer.getType(position - 1).getByteLength(pointer) % length;
    }
    for(long i = 0; i < padLength; i++) {
      pointer.getByteArray().addInt8(getOffset(pointer) + i, (byte) 0);
    }
  }

  @Override
  public void remove(Pointer<?, ? extends Type> pointer) {
    var padLength = length;
    if (align) {
      padLength = pointer.getType(position - 1).getByteLength(pointer) % length;
    }
    for(long i = 0; i < padLength; i++) {
      pointer.getByteArray().removeInt8(getOffset(pointer) + i);
    }
  }
}
