package com.github.moaxcp.x11client;

import java.io.IOException;
import java.util.List;
import lombok.*;

import static com.github.moaxcp.x11client.Depth.readDepth;

@Value
@Builder
public class Screen {
  int root;
  int defaultColorMap;
  int defaultWhitePixel;
  int defaultBlackPixel;
  int currentInputMasks;
  int width;
  int height;
  int widthInMillimeters;
  int heightInMillimeters;
  int minInstalledMaps;
  int maxInstalledMaps;
  int rootVisualId;
  BackingStore backingStore;
  boolean saveUnders;
  int rootDepth;
  @Singular
  List<Depth> allowedDepths;

  public static Screen readScreen(X11Input in) throws IOException {
    ScreenBuilder builder = Screen.builder()
        .root(in.readCard32())
        .defaultColorMap(in.readCard32())
        .defaultWhitePixel(in.readCard32())
        .defaultBlackPixel(in.readCard32())
        .currentInputMasks(in.readCard32())
        .width(in.readCard16())
        .height(in.readCard16())
        .widthInMillimeters(in.readCard16())
        .heightInMillimeters(in.readCard16())
        .rootVisualId(in.readCard32())
        .backingStore(BackingStore.getByCode(in.readCard8()))
        .saveUnders(in.readCard8() == 1)
        .rootDepth(in.readCard8());
    int numberOfDepths = in.readCard8();
    for(int i = 0; i < numberOfDepths; i++) {
      Depth depth = readDepth(in);
      builder.allowedDepth(depth);
    }

    return builder.build();
  }
}
