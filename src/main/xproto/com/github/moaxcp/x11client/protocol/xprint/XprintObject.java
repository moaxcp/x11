package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.XObject;

interface XprintObject extends XObject {
  default String getPluginName() {
    return XprintPlugin.NAME;
  }
}
