package com.github.moaxcp.x11client.protocol.xinerama;

import com.github.moaxcp.x11client.protocol.XObject;

interface XineramaObject extends XObject {
  default String getPluginName() {
    return XineramaPlugin.NAME;
  }
}
