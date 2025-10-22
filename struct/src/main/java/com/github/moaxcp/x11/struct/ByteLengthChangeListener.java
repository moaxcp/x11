package com.github.moaxcp.x11.struct;

@FunctionalInterface
public interface ByteLengthChangeListener {
  static ByteLengthChangeListener align(int alignPosition) {
    return new AlignByteChangeListener(alignPosition);
  }

  class AlignByteChangeListener implements ByteLengthChangeListener {
    private final int alignPosition;

    public AlignByteChangeListener(int alignPosition) {
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

      AlignByteChangeListener that = (AlignByteChangeListener) o;
      return alignPosition == that.alignPosition;
    }

    @Override
    public int hashCode() {
      return alignPosition;
    }
  }

  void byteLengthChanged(Pointer<?, ? extends Type<?>> pointer, long previous, long current);
}
