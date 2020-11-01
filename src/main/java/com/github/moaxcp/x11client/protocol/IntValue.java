package com.github.moaxcp.x11client.protocol;

public interface IntValue {
  int getValue();

  default boolean isEnabled(int value) {
    return false;
  }

  default int enableFor(int value) {
    return value | getValue();
  }

  default int disableFor(int value) {
    return value & ~getValue();
  }
}
