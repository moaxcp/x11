package com.github.moaxcp.x11client;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Utilities {
  public static List<Byte> byteArrayToList(byte[] bytes) {
    List<Byte> list = new ArrayList<>();
    for(byte b : bytes) {
      list.add(b);
    }
    return Collections.unmodifiableList(list);
  }

  public static String byteListToString(List<Byte> byteList, Charset charset) {
    byte[] bytes = new byte[byteList.size()];
    for(int i = 0; i < bytes.length; i++) {
      bytes[i] = byteList.get(i);
    }
    return new String(bytes, charset);
  }

  public static List<Byte> stringToByteList(String string) {
    return byteArrayToList(string.getBytes());
  }
}
