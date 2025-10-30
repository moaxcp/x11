package com.github.moaxcp.x11.struct;

@FunctionalInterface
public interface ByteLengthListener {
  static ByteLengthListener align(int alignPosition) {
    return new AlignByteListener(alignPosition);
  }

  class AlignByteListener implements ByteLengthListener {
    private final int alignPosition;

    public AlignByteListener(int alignPosition) {
      this.alignPosition = alignPosition;
    }

    @Override
    public void byteLengthChanged(Pointer<?, ? extends Type<?>> pointer, long previous, long current) {
      ((PadType) pointer.getType(alignPosition)).reAlign(pointer, previous, current);
    }

    @Override
    public String toString() {
      return "AlignByteChangeListener{" +
          "alignPosition=" + alignPosition +
          '}';
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;

      AlignByteListener that = (AlignByteListener) o;
      return alignPosition == that.alignPosition;
    }

    @Override
    public int hashCode() {
      return alignPosition;
    }
  }

  void byteLengthChanged(Pointer<?, ? extends Type<?>> pointer, long previous, long current);
}
