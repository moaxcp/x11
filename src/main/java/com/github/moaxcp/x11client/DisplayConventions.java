package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.xproto.ScreenStruct;
import com.github.moaxcp.x11client.protocol.xproto.SetupStruct;
import lombok.Value;

@Value
class DisplayConventions {
  SetupStruct setup;


  ScreenStruct getDefaultScreen() {
    return setup.getRoots().get(0);
  }

  int getDefaultRoot() {
    return getDefaultScreen().getRoot();
  }

  byte getDefaultDepth() {
    return getDefaultScreen().getRootDepth();
  }

  int getDefaultVisualId() {
    return getDefaultScreen().getRootVisual();
  }

  boolean hasValidNextResourceFor(int resourceId) {
    return (resourceId + 1 & ~setup.getResourceIdMask()) == 0;
  }

  int maskNextResourceId(int resourceId) {
    return resourceId | setup.getResourceIdBase();
  }
}
