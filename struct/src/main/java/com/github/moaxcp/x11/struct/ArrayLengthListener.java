package com.github.moaxcp.x11.struct;

@FunctionalInterface
public interface ArrayLengthListener {
  enum ArrayLengthReason {
    /**
     * Item requested to be removed from array
     */
    DEALLOCATED,
    /**
     * Item requested to be added to array
     */
    ALLOCATED,
    /**
     * Length field changed and array needs to adjust from previous size
     */
    RESIZED_BY_LENGTH_FIELD
  }

  static ArrayLengthListener lengthField(int position) {
    return new SetLengthFieldListener(position);
  }

  class SetLengthFieldListener implements ArrayLengthListener {
    private final int position;

    public SetLengthFieldListener(int position) {
      this.position = position;
    }

    @Override
    public void arrayLengthChanged(ArrayLengthReason reason, Pointer<?, ? extends Type<?>> pointer, long previous, long current) {
      if(reason == ArrayLengthReason.RESIZED_BY_LENGTH_FIELD) {
        return;
      }
      ((NumberType) pointer.getType(position)).setForArrayLength(pointer, current);
    }

    @Override
    public String toString() {
      return "SetLengthFieldChangeListener{" +
          "position=" + position +
          '}';
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;

      SetLengthFieldListener that = (SetLengthFieldListener) o;
      return position == that.position;
    }

    @Override
    public int hashCode() {
      return position;
    }
  }

  void arrayLengthChanged(ArrayLengthReason reason, Pointer<?, ? extends Type<?>> pointer, long previous, long current);
}
