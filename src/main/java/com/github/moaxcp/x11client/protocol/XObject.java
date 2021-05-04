package com.github.moaxcp.x11client.protocol;

import java.util.List;

public interface XObject {
  static <T extends XObject> int sizeOf(List<T> objects) {
    return objects.stream()
      .mapToInt(XObject::getSize)
      .sum();
  }

  static int getSizeForPadAlign(int size) {
    return getSizeForPadAlign(4, size);
  }

  static int getSizeForPadAlign(int align, int size) {
    return (align - size % align) % align;
  }

  /**
   * length of object expressed in units of 4 bytes
   * @return
   */
  int getSize();

  String getPluginName();
}
