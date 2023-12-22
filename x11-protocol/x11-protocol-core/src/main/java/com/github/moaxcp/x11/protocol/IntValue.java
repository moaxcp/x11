package com.github.moaxcp.x11.protocol;

public interface IntValue {
  int getValue();

  default boolean isEnabled(int value) {
    return (value & getValue()) > 0;
  }

  default int enableFor(int value) {
    return value | getValue();
  }

  default int disableFor(int value) {
    return value & ~getValue();
  }
}
