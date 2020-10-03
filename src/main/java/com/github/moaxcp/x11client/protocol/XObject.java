package com.github.moaxcp.x11client.protocol;

import java.io.IOException;
import java.util.List;

public interface XObject {
  static <T extends XObject> int sizeOf(List<T> objects) {
    return objects.stream()
      .mapToInt(XObject::getSize)
      .sum();
  }
  /**
   * length of object expressed in units of 4 bytes
   * @return
   */
  int getSize();
}
