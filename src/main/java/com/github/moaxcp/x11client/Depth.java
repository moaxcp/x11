package com.github.moaxcp.x11client;

import java.io.IOException;
import java.util.List;
import lombok.*;

@Value
@Builder
public class Depth {
  int depth;
  @Singular
  List<VisualType> visualTypes;

  public static Depth readDepth(X11Input in) throws IOException {
    DepthBuilder builder = Depth.builder()
        .depth(in.readCard8());
    in.readCard8(); //unused
    int numberOfVisualTypes = in.readCard16();
    for(int i = 0; i < numberOfVisualTypes; i++) {
      builder.visualType(VisualType.readVisualType(in));
    }
    return builder.build();
  }
}
