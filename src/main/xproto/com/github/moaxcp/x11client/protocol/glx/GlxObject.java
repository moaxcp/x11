package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.XObject;

interface GlxObject extends XObject {
  default String getPluginName() {
    return GlxPlugin.NAME;
  }
}
