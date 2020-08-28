package com.github.moaxcp.x11client.protocol;

public interface IntValue {
  int getValue();

  default int enableFor(int value) {
    return value | getValue();
  }

  default int disableFor(int value) {
    return value & ~getValue();
  }
}
