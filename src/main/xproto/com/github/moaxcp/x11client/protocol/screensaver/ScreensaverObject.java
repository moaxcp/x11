package com.github.moaxcp.x11client.protocol.screensaver;

import com.github.moaxcp.x11client.protocol.XObject;

interface ScreensaverObject extends XObject {
  default String getPluginName() {
    return ScreensaverPlugin.NAME;
  }
}
