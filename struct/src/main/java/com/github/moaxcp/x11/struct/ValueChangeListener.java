package com.github.moaxcp.x11.struct;

import static com.github.moaxcp.x11.struct.ArrayLengthListener.ArrayLengthReason.RESIZED_BY_LENGTH_FIELD;
import static com.github.moaxcp.x11.struct.ValueChangeListener.ValueChangeReason.SET_VALUE;

public interface ValueChangeListener {

  enum ValueChangeReason {
    /**
     * Array field was added too or removed from so length field needs to adjust
     */
    SET_BY_ARRAY_LENGTH,
    /**
     * Value was set by request
     */
    SET_VALUE
  }

  static ExtendArrayListener extendArrayListener(int position) {
    return new ExtendArrayListener(position);
  }

  class ExtendArrayListener implements ValueChangeListener {
    private final int position;

    public ExtendArrayListener(int position) {
      this.position = position;
    }

    @Override
    public void valueChanged(ValueChangeReason reason, Pointer<?, ? extends Type<?>> pointer, long index, Object oldValue, Object newValue) {
      if (reason != SET_VALUE) {
        return;
      }
      var current = ((Number) newValue).longValue();
      var old = ((Number) oldValue).longValue();
      ValueType<?, ?> type = pointer.getType(position);
      if (current > old) {
        var length = current - old;
        for (long i = 0; i < length; i++) {
          type.allocate(RESIZED_BY_LENGTH_FIELD, pointer, old + i);
        }
      } else {
        var length = old - current;
        for (long i = length - 1; i >= 0; i--) {
          type.remove(RESIZED_BY_LENGTH_FIELD, pointer, old - 1 + i);
        }
      }
    }
  }

  void valueChanged(ValueChangeReason reason, Pointer<?, ? extends Type<?>> pointer, long index, Object oldValue, Object newValue);
}
