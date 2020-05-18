package com.github.moaxcp.x11client;

import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VisualType {
  int visualId;
  VisualTypeClass visualTypeClass;
  int bitsPerRgbValue;
  int colorMapEntries;
  int redMask;
  int greenMask;
  int blueMask;

  public static VisualType readVisualType(X11Input in) throws IOException {
    VisualType visualType = VisualType.builder()
        .visualId(in.readCard32())
        .visualTypeClass(VisualTypeClass.getByCode(in.readCard8()))
        .bitsPerRgbValue(in.readCard8())
        .colorMapEntries(in.readCard16())
        .redMask(in.readCard32())
        .greenMask(in.readCard32())
        .blueMask(in.readCard32())
        .build();
    in.readCard32(); //unused
    return visualType;
  }
}
