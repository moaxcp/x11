package com.github.moaxcp.x11client;

import java.io.IOException;
import lombok.Value;

@Value
public class Format {
  int depth;
  int bitsPerPixel;
  int scanlinePad;

  public static Format readFormat(X11Input in) throws IOException {
    int depth = in.readCard8();
    int bitsPerPixel = in.readCard8();
    int scanlinePad = in.readCard8();
    in.readString8(5);
    return new Format(depth, bitsPerPixel, scanlinePad);
  }
}
