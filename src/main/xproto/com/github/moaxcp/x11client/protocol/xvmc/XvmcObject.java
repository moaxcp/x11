package com.github.moaxcp.x11client.protocol.xvmc;

import com.github.moaxcp.x11client.protocol.XObject;

interface XvmcObject extends XObject {
  default String getPluginName() {
    return XvmcPlugin.NAME;
  }
}
