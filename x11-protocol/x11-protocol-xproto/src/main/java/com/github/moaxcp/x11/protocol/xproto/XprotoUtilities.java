package com.github.moaxcp.x11.protocol.xproto;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class XprotoUtilities {

  public static List<Char2b> toChar2bString(String string) {
    return string.chars()
      .mapToObj(c -> Char2b.builder()
        .byte1((byte) (c >> 8))
        .byte2((byte) c)
        .build())
      .toList();
  }
}
