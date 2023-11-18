package com.github.moaxcp.x11client.protocol.xf86vidmode;

import com.github.moaxcp.x11client.protocol.XObject;

interface Xf86vidmodeObject extends XObject {
  default String getPluginName() {
    return Xf86vidmodePlugin.NAME;
  }
}
