package com.github.moaxcp.x11client.protocol.xkb;

import com.github.moaxcp.x11client.protocol.XObject;

interface XkbObject extends XObject {
  default String getPluginName() {
    return XkbPlugin.NAME;
  }
}
