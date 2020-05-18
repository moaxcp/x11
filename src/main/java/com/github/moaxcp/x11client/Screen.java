package com.github.moaxcp.x11client;

import java.util.List;
import lombok.Builder;
import lombok.Value;

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
  List<Depth> allowedDepths;

  public enum BackingStore {
    NEVER,
    WHEN_MAPPED,
    ALWAYS
  }

}
