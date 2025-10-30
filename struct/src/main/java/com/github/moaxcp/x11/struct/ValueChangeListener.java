package com.github.moaxcp.x11.struct;

import com.github.moaxcp.x11.struct.ArrayLengthListener.Reason;

import static com.github.moaxcp.x11.struct.ArrayLengthListener.Reason.LENGTH_FIELD;

public interface ValueChangeListener {

  static ExtendArrayListener extendArrayListener(int position) {
    return new ExtendArrayListener(position);
  }

  class ExtendArrayListener implements ValueChangeListener {
    private final int position;

    public ExtendArrayListener(int position) {
      this.position = position;
    }

    @Override
    public void valueChanged(Pointer<?, ? extends Type<?>> pointer, long index, Object oldValue, Object newValue) {
      var length = ((Number) newValue).longValue() - ((Number) oldValue).longValue();
      ValueType<?, ?> type = pointer.getType(position);
      if (length > 0) {
        for (long i = 0; i < length; i++) {
          type.allocate(LENGTH_FIELD, pointer, ((Number) oldValue).longValue() + i);
        }
      } else {
        for (long i = 0; i < -length; i++) {
          type.remove(LENGTH_FIELD, pointer, ((Number) oldValue).longValue() + i + length);
        }
      }
    }
  }

  void valueChanged(Pointer<?, ? extends Type<?>> pointer, long index, Object oldValue, Object newValue);
}
