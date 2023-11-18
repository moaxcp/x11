package com.github.moaxcp.x11client.protocol.xselinux;

import com.github.moaxcp.x11client.protocol.XObject;

interface XselinuxObject extends XObject {
  default String getPluginName() {
    return XselinuxPlugin.NAME;
  }
}
