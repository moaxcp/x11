package com.github.moaxcp.x11client.protocol.xc_misc;

import com.github.moaxcp.x11client.protocol.XObject;

interface XcMiscObject extends XObject {
  default String getPluginName() {
    return XcMiscplugin.NAME;
  }
}
