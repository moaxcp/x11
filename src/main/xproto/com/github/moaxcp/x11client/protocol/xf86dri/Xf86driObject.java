package com.github.moaxcp.x11client.protocol.xf86dri;

import com.github.moaxcp.x11client.protocol.XObject;

interface Xf86driObject extends XObject {
  default String getPluginName() {
    return Xf86driPlugin.NAME;
  }
}
